package net.umbriel.kryotest;

import net.umbriel.kryolib.*;


import java.io.*;

public class KryoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			StreamReader sr = new StreamReader(new File("flossy1"));
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			System.out.println("Info string from track 0:");
			StreamTrack track = sr.getTrack(0, 0);
			System.out.println(track.getInfoText());
			System.out.println(track.getSampleClock());
		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
