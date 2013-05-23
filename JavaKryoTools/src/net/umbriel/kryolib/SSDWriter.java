package net.umbriel.kryolib;

import java.util.ArrayList;
import net.umbriel.imageformat.*;

public class SSDWriter implements Decoder {


	@Override
	public Track decode(ArrayList<Boolean> b) {

		//Marks that are important...
		
		//Index Address Mark
		String indexMark="1111011101111010";
		
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
		CRCCalculator crcChecker = new CRCCalculator(); //CRC calc.
		int byteCounter =0; //Data bytes
		int sectorByteCounter=0; //Bytes in sector header


		int side=0xff;
		int sectorSize=0xff;


		Track track = new Track();
		Sector currentSector = new Sector();
		ArrayList<Integer> sectorData = new ArrayList<Integer>();

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
			
			if (indexMark.equals(check)) {
				System.out.println("IAM found");
			}
			
			//Are we at the start of a new sector? 
			if (sectorMark.equals(check)) {
				//Read sector information
				//System.out.println("FM Sector found.");
				currentSector = new Sector();
				track.getSectors().add(currentSector); //Add it to the track...
				readingData = false;
				clock = true;
				readingSectorInfo = true;
				sectorByteCounter=0;
				currentByte.setLength(0);
				crcChecker.reset(); // Reset CRC
				crcChecker.updateCRC(0xFE); //CRC starts with the IM
				side=0xff;
				sectorSize=0xff;
				//Clock is now in sync, and true...
			}

			if (check.equals(dataMark)) {
				//Do we have a Data address mark?
				System.out.print(" DAM ");
				sectorData=new ArrayList<Integer>();
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
					track.setTrackNumber(value);
					currentSector.setTrackNumber(value);
				}
				if (sectorByteCounter==1) {
					side=value;
				}
				if (sectorByteCounter==2) {
					currentSector.setNumber(value);
				}
				if (sectorByteCounter==3) {
					currentSector.setSize(value);
					sectorSize=0x80<<(value & 0x03); //Translate the sector size byte
				}
				if (sectorByteCounter==4) {
					currentSector.setHeaderChecksum(value<<8);
				}
				if (sectorByteCounter==5)  {
					currentSector.setHeaderChecksum(currentSector.getHeaderChecksum()+value);
				}
				if (sectorByteCounter==6) { //Read through to the 2 CRC bytes. crc will be 0 if it's OK.
					if (crcChecker.getCrc()==0) {
						System.out.print("T:"+currentSector.getTrackNumber()+" S:"+side+" Sr:"+
								currentSector.getNumber()+" Size:"+
								currentSector.getSize()+" IM CRC OK");
					} else {
						System.out.print("T:"+currentSector.getTrackNumber()+" S:"+side+" Sr:"+
								currentSector.getNumber()+" Size:"+
								currentSector.getNumber()+" IM CRC FAIL");
					}
				}

				sectorByteCounter++;
				//Push the check into the crc...
				crcChecker.updateCRC(value);

			}

			if (currentByte.length()==8 && readingData) {
				int value = Integer.parseInt(currentByte.toString(), 2);
				if (byteCounter<sectorSize) { //don't overflow the array
					sectorData.add(value);
				}
				if (byteCounter==sectorSize) { //checksum byte 1
					currentSector.setDataChecksum(value<<8);
				}
				if (byteCounter==sectorSize+1) { //checksum byte 2
					currentSector.setDataChecksum(currentSector.getDataChecksum()+value);
				}
				//System.out.print(Integer.toHexString(value & 0xFF)+",");
				//System.out.print((char)value);
				byteCounter++;
				crcChecker.updateCRC(value);
				if (byteCounter ==sectorSize+2) { //data unit + 2 byte CRC

					if (crcChecker.getCrc()==0) { //If data is OK CRC will now be 0.
						System.out.println(" Data Size="+ sectorSize+" Data CRC OK");
					} else {
						System.out.println(" Data Size="+ sectorSize+" Data CRC FAIL");
					}

					currentSector.setData(sectorData); //set the sector data
					readingData=false;
				}
			}
		}
		if (byteCounter<sectorSize+2) {
			currentSector.setData(sectorData);
			System.out.println(byteCounter+" bytes of data read on last sector before index.");
		}
		// TODO Auto-generated method stub
		return track;
	}

}
