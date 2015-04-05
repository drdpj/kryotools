package net.umbriel.kryolib;

import java.util.ArrayList;
/**
 *     Copyright (C) 2013  Daniel Jameson

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

/**
 * @author daniel
 *
 */
public class StreamTrack {
	
	private Double sampleClock;
	private Double indexClock;
	private String infoText;
	private ArrayList<Flux> fluxes;   //All of the fluxes
	private ArrayList<Index> indexes; //Indexes
	private ArrayList<OOBBlock> oobBlocks; //Raw OOB Blocks
	
	
	public StreamTrack() {
		setSampleClock(0.0);
		setIndexClock(0.0);
		setInfoText("");
		fluxes = new ArrayList<Flux>();
		indexes = new ArrayList<Index>();
		setOobBlocks(new ArrayList<OOBBlock>());
	}
	
	/**
	 * Fluxes are stored as longs and each one is the number of clock ticks
	 * between 
	 * 
	 * @return ArrayList<Long> of fluxes
	 */
	public ArrayList<Flux> getFluxes() {
		return fluxes;
	}
	/**
	 * @param Sets an ArrayList of fluxes
	 */

	protected void setFluxes(ArrayList<Flux> fluxes) {
		this.fluxes = fluxes;
	}
	/**
	 * @return Indexes for the track.
	 */
	public ArrayList<Index> getIndexes() {
		return indexes;
	}
	/**
	 * @param indexes set a new ArrayList of indexes
	 */
	protected void setIndexes(ArrayList<Index> indexes) {
		this.indexes = indexes;
	}
	
	/**
	 * @return Number of reads of this track
	 */
	public Integer trackReads() {
		return indexes.size()-1;
	}

	/**
	 * @return the sampleClock
	 */
	public Double getSampleClock() {
		return sampleClock;
	}

	/**
	 * @param sampleClock the sampleClock to set
	 */
	public void setSampleClock(Double sampleClock) {
		this.sampleClock = sampleClock;
	}

	/**
	 * @return the indexClock
	 */
	public Double getIndexClock() {
		return indexClock;
	}

	/**
	 * @param indexClock the indexClock to set
	 */
	public void setIndexClock(Double indexClock) {
		this.indexClock = indexClock;
	}

	/**
	 * @return the infoText
	 */
	public String getInfoText() {
		return infoText;
	}

	/**
	 * @param infoText the infoText to set
	 */
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}

	/**
	 * @return the oobBlocks
	 */
	public ArrayList<OOBBlock> getOobBlocks() {
		return oobBlocks;
	}

	/**
	 * @param oobBlocks the oobBlocks to set
	 */
	public void setOobBlocks(ArrayList<OOBBlock> oobBlocks) {
		this.oobBlocks = oobBlocks;
	}

}
