package net.umbriel.kryolib.discstructure;

/**
 *  (c) 2018 Daniel Jameson
 *     
 *  This program is free software: you can redistribute it and/or modify
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
