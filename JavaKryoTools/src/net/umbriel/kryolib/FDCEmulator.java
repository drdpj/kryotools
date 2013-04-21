package net.umbriel.kryolib;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class FDCEmulator {

	/**
	 * Emulate FDC and return bits.
	 * Variable rotational speed to deal with CAV formats too.
	 * Just work from the first reading or do we composite all revolutions available?
	 */

	/**
	 * Store bits in a BitSet...
	 * Cell size? 2000ns
	 * Some notes:
	 * FM encoding? 2000 (11), 4000 (01)
	 * MFM encoding? 4000 (01) , 6000 (001), 8000 (0001) gaps...
	 * Not worried at this point, all we do is return bits...
	 * 
	 */

	private StreamTrack track;

	private Double rpm = 0.0;
	private Double maxRpm = 0.0;
	private Double minRpm = 0.0;
	private int clockCentre = 2000;
	private Integer tolerance = 20; //Percentage tolerance for cell-size
	private ArrayList<Boolean> binaryStream;
	private int readNumber =0;


	
	/**
	 * @return the clock centre (2000 = DD, 1000 = QD)
	 */
	public int getClockCentre() {
		return clockCentre;
	}

	/**
	 * @param clockCentre the clockCentre to set ( 4000=SD, 2000 = DD, 1000 = QD)
	 */
	public void setClockCentre(int clockCentre) {
		this.clockCentre = clockCentre;
		binaryStream = new ArrayList<Boolean>();
	}

	public FDCEmulator(StreamTrack t) {
		this.track=t;
		binaryStream = new ArrayList<Boolean>();
		processTrack();
	}
	
	public FDCEmulator() {
		binaryStream=new ArrayList<Boolean>();
	}

	/**
	 * Accepts a stream track.
	 * @param s
	 */
	public void setTrack(StreamTrack t) {
		this.track=t;

		processTrack();
	}



	/**
	 * This processes the track - if you change any parameters it can be run
	 * to re-process
	 */
	public void processTrack() {
		if (this.track!=null) {
			/**
			 * The actual flux time is the long int value divided by
			 * the sample clock...
			 */

			//Let's assume we're just working with the first revolution for now...
			int firstIndex = track.getIndexes().get(0).getFluxIndex();
			int lastIndex = track.getIndexes().get(1).getFluxIndex();

			ArrayList<Flux> fluxes = track.getFluxes();

			int clockedZeros=0; // number of 0s we've encountered...
			int clock=clockCentre;
			double heldOverTime = 0.0;
			double clockMin = ((clockCentre*(100-tolerance))/100);
			double clockMax = ((clockCentre*(100+tolerance))/100);
			// Start at the start...
			for (int i=firstIndex; i<lastIndex; i++) {
				double time = fluxes.get(i).getNanoSecondTime();
				time+= heldOverTime; // We're not snapping each window each time...
				clockedZeros=0;
				while (time>=0) {

					time -= clock;
					if (time>=clock/2) {
						clockedZeros++;
						binaryStream.add(new Boolean(false));
					} else {
						if ((clockedZeros>=1) && (clockedZeros<=3)) { //In sync... Adjust by 10% phase mismatch
							int diff = (int) (time/(clockedZeros+1));
							clock+=(diff/10);
						} else { // out of sync... adjust base towards centre...
							clock += (clockCentre-clock)/10;

							clock = (int)(Math.max(clockMin, Math.min(clockMax, clock)));
						}
						binaryStream.add(new Boolean(true));
						heldOverTime=time/2;
						time=-1;
					}
				}
			}


		} //else throw some error.

	}

	/**
	 * @return the rpm
	 */
	public Double getRpm() {
		return rpm;
	}

	/**
	 * @param rpm the rpm to set
	 */
	public void setRpm(Double rpm) {
		this.rpm = rpm;
	}

	/**
	 * @return the maxRpm
	 */
	public Double getMaxRpm() {
		return maxRpm;
	}

	/**
	 * @param maxRpm the maxRpm to set
	 */
	public void setMaxRpm(Double maxRpm) {
		this.maxRpm = maxRpm;
	}

	/**
	 * @return the minRpm
	 */
	public Double getMinRpm() {
		return minRpm;
	}

	/**
	 * @param minRpm the minRpm to set
	 */
	public void setMinRpm(Double minRpm) {
		this.minRpm = minRpm;
	}

	public String getBinaryString() {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<binaryStream.size();i++) {
			builder.append(binaryStream.get(i).booleanValue()?"1":"0");
		}
		return builder.toString();
	}
	
	
	public ArrayList<Boolean> getBinaryList() {
		return binaryStream;
	}
	
	/**
	 * Returns the contents of the bitstream as an array of bytes
	 * @param index index in the bitstream to start from
	 * @return byte array of index stream
	 */
	public byte[] getTrackByteArray(int index) {
		ByteArrayOutputStream arrayOfBytes = new ByteArrayOutputStream();
		for (int i=index; i<binaryStream.size()-8;i+=8) {
			StringBuilder builder = new StringBuilder();
			for (int j=0; j<8; j++) {
				builder.append(binaryStream.get(i+j).booleanValue()?"1":"0");
			}
			arrayOfBytes.write(Integer.parseInt(builder.toString(),2));
		}


		byte[] trackBytes = arrayOfBytes.toByteArray();
		return trackBytes;
	}

	/**
	 * @return the read of the floppy that's being worked from
	 */
	public int getReadNumber() {
		return readNumber;
	}

	/**
	 * @param readNumber which read from the floppy to work from (you need to check 
	 */
	public void setReadNumber(int readNumber) {
		this.readNumber = readNumber;
	}




}
