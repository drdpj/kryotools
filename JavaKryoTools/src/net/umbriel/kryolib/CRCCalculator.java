package net.umbriel.kryolib;

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

public class CRCCalculator {

	//Char is actually a 16bit unsigned value, so this makes some level of sense...
	private char[] crc_citt = new char[256];
	private char crc = (char)0xFFFF;
	
	
	/**
	 * Constructor, generates lookup table
	 */
	public CRCCalculator() {
		
		//Lookup table...
		
		for (int i=0;i<256;i++) {
			char w = (char) (i << 8);
			for (int j=0; j<8; j++) {
				w=(char)  (((int)w<<1) ^ ((int)w>>15 & 1) * 0x1021);
			}
			crc_citt[i]=w;
			//System.out.println(i+":"+Integer.toBinaryString((int)w));
		}
		
	}
	
	/**
	 * 
	 * @param i 16bit value to check against the current CRC
	 * @return true if crc is valid.
	 */
	public boolean checkCRC(int i) {
		if ((i^(int)crc) ==0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param i byte to update CRC with.
	 */
	public void updateCRC(int i) {
		//crc = (char)(((int)crc << 8) ^ (int)crc_citt[(((int)crc >> 8) & 0xff)] ^ i);
		//crc = (crc<<8 EOR table%!((crc>>8 EOR value)<<2)) AND &FFFF
//		crc= (char)(((int)crc << 8) ^ (int)crc_citt[(((int)crc>>8) ^ i)] & 0xffff);
		for (int j = 0; j < 8; j++) {
            boolean bit = ((i   >> (7-j) & 1) == 1);
            boolean c15 = ((crc >> 15    & 1) == 1);
            crc <<= 1;
            if (c15 ^ bit) crc ^= 0x1021;
         }

	}
	
	public void reset() {
		crc = (char)0xFFFF;
	}
	
	public void printCRC() {
		System.out.println(Integer.toHexString((int)crc));
	}
	public int getCrc() {
		return (int)crc;
	}
 
}
