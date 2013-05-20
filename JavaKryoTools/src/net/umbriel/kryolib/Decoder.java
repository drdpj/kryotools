package net.umbriel.kryolib;

import java.util.ArrayList;

import net.umbriel.imageformat.Track;

/**
 * Interface for mfm/fm decoders...
 * @author mqbssdpj
 *
 */

public interface Decoder {
	public Track decode(ArrayList<Boolean> b);

}
