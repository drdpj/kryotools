package net.umbriel.kryolib;

import java.util.ArrayList;

public class SSDWriter implements Decoder {

	Integer[][] track = new Integer[10][256];

	@Override
	public Integer[][] decode(ArrayList<Boolean> b) {

		//Marks that are important...
		//Sector ID address Mark
		String sectorMark="1111010101111110";

		//Data address Mark
		String dataMark="1111010101101111";

		//Empty string for checking...
		String check = "";
		boolean clock = true; // True when next bit is expected to be the clock
		boolean readingSectorInfo = false; // True when in the sector header
		boolean readingData = false; // True when reading data
		StringBuilder currentByte=new StringBuilder(); //Builds up byte
		StringBuilder crc = new StringBuilder(); //Builds up CRC
		CRCCalculator crcChecker = new CRCCalculator(); //CRC calc.
		int byteCounter =0;
		int sectorByteCounter=0;
		int sectorNumber=0;
		int crcByteCounter =0;
		int readCRC=0;
		boolean doCRC=false;
		int trackNumber=0xff;
		int sector=0xff;
		int side=0xff;
		int sectorSize=0xff;

		//Loop, shifting through a bit at a time
		for (int i=0; i<b.size(); i++) {
			if (currentByte.length()==8) { //new byte
				currentByte.setLength(0);
			}
			Boolean currentBit = b.get(i);

			if (readingSectorInfo && !clock) {
				currentByte.append((currentBit.booleanValue()?"1":"0"));
				clock = true;
			} else if (readingSectorInfo && clock) {
				clock=false;
			} else if (readingData && !clock) {
				currentByte.append((currentBit.booleanValue()?"1":"0"));
				clock=true;
			}
			else if (readingData && clock) {
				clock=false;
			}
			//Shift bit into AM comparator & Data Mark comparator
			if (check.length()==16) { // Need to shunt along one...
				check = check.substring(1, 16);
			}
			check=check+(currentBit.booleanValue()?"1":"0");
			//Are we at the start of a new sector? 
			if (sectorMark.equals(check)) {
				//Read sector information
				//System.out.println("FM Sector found.");
				readingData = false;
				clock = true;
				readingSectorInfo = true;
				sectorByteCounter=0;
				currentByte.setLength(0);
				crcChecker.reset(); // Reset CRC
				crcChecker.updateCRC(0xFE); //CRC starts with the IM
				crcByteCounter=0;
				readCRC=0;
				doCRC=false;
				trackNumber=0xff;
				sector=0xff;
				side=0xff;
				sectorSize=0xff;
				//Clock is now in sync, and true...
			}

			if (check.equals(dataMark)) {
				//Do we have a Data address mark?
				//System.out.println("FM Data found.");
				byteCounter=0;
				readingData = true;
				clock = true;
				readingSectorInfo = false;
				currentByte.setLength(0);
				crcChecker.reset();
				crcChecker.updateCRC(0xFB);//CRC starts with the DM
				//Read Data
			}
			if (currentByte.length()==8 && readingSectorInfo) {
				int value = Integer.parseInt(currentByte.toString(), 2);
				if (sectorByteCounter==0) {
					trackNumber=value;
				}
				if (sectorByteCounter==1) {
					side=value;
				}
				if (sectorByteCounter==2) {
					sectorNumber=value;
				}
				if (sectorByteCounter==3) {
					sectorSize=value;
				}
				if (sectorByteCounter==6) {
					if (crcChecker.getCrc()==0) {
						System.out.print("T:"+trackNumber+" S:"+side+" Sr:"+sectorNumber+" IM CRC OK");
					} else {
						System.out.print("T:"+trackNumber+" S:"+side+" Sr:"+sectorNumber+" IM CRC FAIL");
					}
				}

				sectorByteCounter++;
				//Push the check into the crc...
				crcChecker.updateCRC(value);

			}

			if (currentByte.length()==8 && readingData) {
				int value = Integer.parseInt(currentByte.toString(), 2);
				if (byteCounter<256) { //don't overflow the array
					track[sectorNumber][byteCounter]=value;
				}
				//System.out.print(Integer.toHexString(value & 0xFF)+",");
				//System.out.print((char)value);
				byteCounter++;
				crcChecker.updateCRC(value);
				if (byteCounter ==258) { //data unit + 2 byte CRC

					if (crcChecker.getCrc()==0) {
						System.out.println(" Data Size="+ sectorSize+" Data CRC OK");
					} else {
						System.out.println(" Data Size="+ sectorSize+" Data CRC FAIL");
					}

					
					byteCounter=0;

				}
			}
		}

		// TODO Auto-generated method stub
		return track;
	}

}
