package net.umbriel.kryotest;

import net.umbriel.kryolib.*;


import java.io.*;
import java.math.BigInteger;

public class KryoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		try {
			
			
			StreamReader sr = new StreamReader(new File("zarch"));
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			FDCEmulator fdc = new FDCEmulator(sr.getTrack(41, 0));
			System.out.println(fdc.getBinaryString());
			fdc.setClockCentre(2000);
			fdc.processTrack();
			System.out.println(fdc.getBinaryString());

		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		APDWriter.createAPD(new File("zarch"));

	}

}
