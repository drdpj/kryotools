package net.umbriel.kryolib;

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
			System.out.println(i+":"+Integer.toBinaryString((int)w));
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
		crc = (char)(((int)crc << 8) ^ (int)crc_citt[(((int)crc >> 8) & 0xff)] ^ i);
	}
	
	public void reset() {
		crc = (char)0xFFFF;
	}

}
