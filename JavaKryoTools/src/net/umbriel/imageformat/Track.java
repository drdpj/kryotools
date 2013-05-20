package net.umbriel.imageformat;

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
		Collections.sort(sectors); //Sort the sectors into numerical order...
		return sectors;
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
