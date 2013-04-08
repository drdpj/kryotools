package net.umbriel.kryolib;

import java.util.BitSet;

public class FDCEmulator {

	/**
	 * Emulate FDC and return bits.
	 * Variable rotational speed to deal with CAV formats too.
	 *  
	 */
	
	/**
	 * Store bits in a BitSet...
	 * Cell size?
	 * Some notes:
	 * FM encoding
	 * MFM encoding
	 * 
	 */
	
	private StreamTrack track;
	
	
	public FDCEmulator(StreamTrack t) {
		this.track=t;
	}
	
	/**
	 * Accepts a stream track.
	 * @param s
	 */
	public void setTrack(StreamTrack t) {
		this.track=t;
	}
	
	/**
	 * return to start of track.
	 */
	
	public void zeroIndex() {
		
	}
	
	public int nextBit() {
		return 0;
	}
	
	public BitSet getBits() {
		return null;
	}
	
}
