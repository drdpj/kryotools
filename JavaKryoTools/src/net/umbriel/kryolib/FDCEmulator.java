package net.umbriel.kryolib;

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
	private long cellPosition;
	private int cellSize;
	private BitSet bits;
	private Boolean allRevolutions = false;
	private Boolean isProcessed = false;
	private Double rpm = 0.0;
	private Double maxRpm = 0.0;
	private Double minRpm = 0.0;
	private Double cellsize = 2.0; //This will be half if HD...
	private Integer tolerance = 10; //Percentage tolerance for cell-size
	
	
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
			for (int i=firstIndex; i<lastIndex; i++) {
				System.out.println((fluxes.get(i).getTime()/track.getSampleClock())*100000); //in micro-s
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
	
	
	
}
