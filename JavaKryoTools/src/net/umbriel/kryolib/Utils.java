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

/**
 * 
 * Utilities
 * @author daniel
 *
 */

import java.util.*;



final class Utils {

	final static Boolean DEBUG = true;

	final static Integer maxIntValue(ArrayList<Integer> ints) {
		Integer maxValue = Integer.MIN_VALUE;
		Iterator<Integer> iter = ints.iterator();
		while (iter.hasNext()) {
			maxValue = Math.max(iter.next(),maxValue);
		}

		return maxValue;
	}

	final static Integer minIntValue(ArrayList<Integer> ints) {
		Integer minValue = Integer.MAX_VALUE;
		Iterator<Integer> iter = ints.iterator();
		while (iter.hasNext()) {
			minValue = Math.min(iter.next(), minValue);
		}

		return minValue;
	}

	final static Double maxDoubleValue(ArrayList<Double> doubles) {
		Double maxValue = Double.MIN_VALUE;
		Iterator<Double> iter = doubles.iterator();
		while (iter.hasNext()) {
			maxValue = Math.max(iter.next(), maxValue);
		}

		return maxValue;
	}

	final static Double minDoubleValue(ArrayList<Double> doubles) {
		Double minValue = Double.MIN_VALUE;
		Iterator<Double> iter = doubles.iterator();
		while (iter.hasNext()) {
			minValue = Math.min(iter.next(), minValue);
		}

		return minValue;
	}

	/**
	 * 
	 * @param data array of bytes
	 * @param pos position to set in the stream
	 * @param val 
	 */
	final static void setBit(byte[] data, int pos, int val) {
		int posByte = pos/8; 
		int posBit = pos%8;
		byte oldByte = data[posByte];
		oldByte = (byte) (((0xFF7F>>posBit) & oldByte) & 0x00FF);
		byte newByte = (byte) ((val<<(8-(posBit+1))) | oldByte);
		data[posByte] = newByte;
	}
	
	/**
	 * 
	 * @param data
	 * @param pos
	 * @return
	 */
	final static int getBit(byte[] data, int pos) {
		int posByte = pos/8; 
		int posBit = pos%8;
		byte valByte = data[posByte];
		int valInt = valByte>>(8-(posBit+1)) & 0x0001;
		return valInt;
	}
	
	/**
	 * 
	 * @param in
	 * @param len
	 * @param step
	 * @return
	 */
	final static byte[] rotateLeft(byte[] in, int len, int step) {
		int numOfBytes = (len-1)/8 + 1;
		byte[] out = new byte[numOfBytes];
		for (int i=0; i<len; i++) {
			int val = getBit(in,(i+step)%len);
			setBit(out,i,val);
		}
		return out;
	}


}
