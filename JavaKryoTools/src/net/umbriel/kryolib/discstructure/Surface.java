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

public class Surface {


	private Integer surface;
	private ArrayList<Track> tracks;
	
	public Surface() {
		setSurface(0);
		setTracks(new ArrayList<Track>());
	}

	/**
	 * @return the surface
	 */
	public Integer getSurface() {
		return surface;
	}

	/**
	 * @param surface the surface to set
	 */
	public void setSurface(Integer surface) {
		this.surface = surface;
	}

	/**
	 * @return the tracks
	 */
	public ArrayList<Track> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	

}
