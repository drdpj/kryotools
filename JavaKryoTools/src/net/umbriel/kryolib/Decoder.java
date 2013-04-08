package net.umbriel.kryolib;

import java.util.BitSet;

/**
 * Interface for mfm/fm decoders...
 * @author mqbssdpj
 *
 */

public interface Decoder {
	public Integer[] decode(BitSet b);

}
