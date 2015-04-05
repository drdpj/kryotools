package net.umbriel.kryolib.discstructure;

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
