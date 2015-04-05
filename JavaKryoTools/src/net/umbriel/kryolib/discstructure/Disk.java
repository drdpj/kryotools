package net.umbriel.kryolib.discstructure;

public class Disk {

	private Surface[] surface;
	
	public Disk() {
		setSurface(new Surface[2]);//we only get two surfaces, honestly
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
