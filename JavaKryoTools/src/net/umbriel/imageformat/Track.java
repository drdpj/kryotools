package net.umbriel.imageformat;

import java.util.ArrayList;
import java.util.Collections;

public class Track {

	private ArrayList<Sector> sectors;
	
	public Track() {
		setSectors(new ArrayList<Sector>());
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
	

}
