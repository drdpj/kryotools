package net.umbriel.kryotest;

import net.umbriel.kryolib.*;


import java.io.*;


public class KryoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FMDecoder decoder = new FMDecoder();
		try {
			
		
			StreamReader sr = new StreamReader(new File("zap!"));
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			FDCEmulator fdc = new FDCEmulator(sr.getTrack(0, 0));
			//System.out.println(fdc.getBinaryString());
			fdc.setClockCentre(4000);
			fdc.processTrack();
			Integer[] z=decoder.decode(fdc.getBinaryList());
			//System.out.println(fdc.getBinaryString());

		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//APDWriter.createAPD(new File("zarch"));

	}

}
