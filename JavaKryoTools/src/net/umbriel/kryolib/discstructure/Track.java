package net.umbriel.kryolib.discstructure;

import java.util.ArrayList;
import java.util.Collections;

public class Track {

	private ArrayList<Sector> sectors;
	private Integer trackNumber;
	
	public Track() {
		setSectors(new ArrayList<Sector>());
		setTrackNumber(0);
	}

	/**
	 * @return the sectors
	 */
	public ArrayList<Sector> getSectors() {
		return sectors;
	}

	public ArrayList<Sector> getSortedSectors() {
		@SuppressWarnings("unchecked")
		ArrayList<Sector> sortedSectors = (ArrayList<Sector>) sectors.clone();
		Collections.sort(sortedSectors);
		return sortedSectors;
	}
	/**
	 * @param sectors the sectors to set
	 */
	public void setSectors(ArrayList<Sector> sectors) {
		this.sectors = sectors;
	}

	/**
	 * @return the trackNumber
	 */
	public Integer getTrackNumber() {
		return trackNumber;
	}

	/**
	 * @param trackNumber the trackNumber to set
	 */
	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
	}
	

}
