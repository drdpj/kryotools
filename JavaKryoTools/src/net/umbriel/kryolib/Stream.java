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
package net.umbriel.kryolib;

import java.util.ArrayList;

/**
 * 
 * Class to represent a single kryoflux stream.
 * @author daniel
 *
 */
public class Stream {

	private ArrayList<StreamTrack> tracks;
	
	/**
	 * No arguments for constructor.
	 */
	
	public Stream() {
		tracks = new ArrayList<StreamTrack>();
	}

	/**
	 * @return ArrayList of all the tracks in the stream.
	 */
	public ArrayList<StreamTrack> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks ArrayList of tracks for this stream object.
	 */
	public void setTracks(ArrayList<StreamTrack> tracks) {
		this.tracks = tracks;
	}
	
	/**
	 * @param index - track to return
	 * @return a StreamTrack object containing the requested track
	 */
	public StreamTrack getTrack(int index) {
		return tracks.get(index);
	}
	
}
