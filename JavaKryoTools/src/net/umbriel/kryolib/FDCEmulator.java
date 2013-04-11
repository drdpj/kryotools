package net.umbriel.kryolib;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.BitSet;

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
	private Boolean allRevolutions = false;
	private Boolean isProcessed = false;
	private Double rpm = 0.0;
	private Double maxRpm = 0.0;
	private Double minRpm = 0.0;
	private int clockCentre = 2000;
	private Integer tolerance = 10; //Percentage tolerance for cell-size
	private ByteArrayOutputStream arrayOfBytes = new ByteArrayOutputStream();
	private byte[] trackBytes;


	public FDCEmulator(StreamTrack t) {
		this.track=t;
		processTrack();
	}

	/**
	 * Accepts a stream track.
	 * @param s
	 */
	public void setTrack(StreamTrack t) {
		this.track=t;
		processTrack();
	}


	public BitSet getBits() {
		return null;
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
			StringBuffer bits = new StringBuffer();
			double heldOverTime = 0.0;
			double clockMin = ((clockCentre*(100-tolerance))/100);
			double clockMax = ((clockCentre*(100+tolerance))/100);
			int byteCounter=0;
			BitSet smallSet = new BitSet();
			// Start at the start...
			for (int i=firstIndex; i<lastIndex; i++) {
				double time = fluxes.get(i).getNanoSecondTime();
				time+= heldOverTime; // We're not snapping each window each time...
				clockedZeros=0;
				while (time>=0) {
					if (byteCounter==8) {
						byteCounter=0;
						arrayOfBytes.write(new Byte(smallSet.toByteArray()[0]));
					}
					time -= clock;
					if (time>=clock/2) {
						clockedZeros++;
						bits.append('0');
						smallSet.clear(byteCounter);
						byteCounter++;
					} else {
						if ((clockedZeros>=1) && (clockedZeros<=3)) { //In sync... Adjust by 10% phase mismatch
							int diff = (int) (time/(clockedZeros+1));
							clock+=(diff/10);
						} else { // out of sync... adjust base towards centre...
							clock += (clockCentre-clock)/10;

							clock = (int)(Math.max(clockMin, Math.min(clockMax, clock)));
						}
						smallSet.set(byteCounter);
						byteCounter++;
						heldOverTime=time/2;
						time=-1;
					}
				}
			}
			if (byteCounter>0) {
				arrayOfBytes.write(smallSet.toByteArray()[0]);
			}
			trackBytes = arrayOfBytes.toByteArray(); // This is it as it stands...


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

	/**
	 * @return the trackBytes
	 */
	public byte[] getTrackBytes() {
		return trackBytes;
	}

	/**
	 * @param trackBytes the trackBytes to set
	 */
	public void setTrackBytes(byte[] trackBytes) {
		this.trackBytes = trackBytes;
	}



}
