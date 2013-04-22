package net.umbriel.kryolib;

import java.util.ArrayList;

public class FMDecoder implements Decoder {

	@Override
	public Integer[] decode(ArrayList<Boolean> b) {
		
		//Marks that are important...
		//Sector ID address Mark
		String sectorMark="1111010101111110";
		
		//Data address Mark
		String dataMark="1111010101101111";
		
		//Empty string for checking...
		String check = "";
		boolean clock = true;
		boolean readingSectorInfo = false;
		boolean readingData = false;
		StringBuilder currentByte=new StringBuilder();
		int byteCounter =0;
		
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
			}
			
			if (readingData && !clock) {
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
				System.out.println("FM Sector found.");
				readingData = false;
				clock = true;
				readingSectorInfo = true;
				currentByte.setLength(0);
				//Clock is now in sync, and true...
			}
			
			if (check.equals(dataMark)) {
				//Do we have a Data address mark?
				System.out.println("FM Data found.");
				byteCounter=0;
				readingData = true;
				clock = true;
				readingSectorInfo = false;
				currentByte.setLength(0);
				//Read Data
			}
			if (currentByte.length()==8 && readingSectorInfo) {
				int value = Integer.parseInt(currentByte.toString(), 2);
				System.out.print(Integer.toHexString(value & 0xFF));
				System.out.println(" Check:"+check);
			}
			
			if (currentByte.length()==8 && readingData) {
				int value = Integer.parseInt(currentByte.toString(), 2);
				//System.out.print(Integer.toHexString(value & 0xFF)+",");
				System.out.print((char)value);
				byteCounter++;
				if (byteCounter ==256) {
					System.out.println();
					System.out.println("256 bytes counted. CRC+gap:");
					byteCounter = 0;
				}
			}
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
