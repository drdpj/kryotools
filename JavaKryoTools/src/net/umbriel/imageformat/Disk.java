package net.umbriel.imageformat;

public class Disk {

	private Surface[] surface;
	
	public Disk() {
		setSurface(new Surface[2]);
	}

	/**
	 * @return the surface
	 */
	public Surface[] getSurface() {
		return surface;
	}

	/**
	 * @param surface the surface to set
	 */
	public void setSurface(Surface[] surface) {
		this.surface = surface;
	}
	
}
