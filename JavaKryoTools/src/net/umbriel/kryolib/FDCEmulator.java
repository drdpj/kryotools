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
	 * Cell size?
	 * Some notes:
	 * FM encoding?
	 * MFM encoding? 4000, 6000, 8000 gaps...
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
	private Double cellsize = 2000.00; //This will be half if HD...
	
	
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
				System.out.println((fluxes.get(i).getTime()/track.getSampleClock())/(0.000000001));
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
