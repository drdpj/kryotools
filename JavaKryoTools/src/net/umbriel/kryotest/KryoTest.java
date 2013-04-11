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
		
		try {
			StreamReader sr = new StreamReader(new File("flossy1"));
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			System.out.println("Info string from track 0:");
			StreamTrack track = sr.getTrack(0, 0);
			System.out.println(track.getInfoText());
			System.out.println(track.getSampleClock());
			System.out.println(track.getIndexClock());
			FDCEmulator fdc = new FDCEmulator(track);
			String byteString = fdc.getBinaryString();
			String mfmTrackMark = "0"+Integer.toBinaryString(0x4489);
			String fmTrackMark = Integer.toBinaryString(0xFFFF)+Integer.toBinaryString(0xAAAA);
			mfmTrackMark = mfmTrackMark+mfmTrackMark;
			System.out.println(mfmTrackMark);
			int mfmTrackIndexLocation = byteString.indexOf(mfmTrackMark);
			int fmTrackIndexLocation = byteString.indexOf(fmTrackMark);
			System.out.println("mfm Index location:"+mfmTrackIndexLocation);
			System.out.println("fm Index location:"+fmTrackIndexLocation);
			System.out.println(byteString.substring(mfmTrackIndexLocation, mfmTrackIndexLocation+60));
			byte[] bytes = fdc.getTrackByteArray(mfmTrackIndexLocation);
			for (int i=0; i<20;i++) {
				int t = bytes[i] & 0xFF;
				String bin = Integer.toBinaryString(t);
				if (bin.length()<8) {
					while (bin.length()<8) {
						bin = "0"+bin;
					}
				}
				System.out.print(bin);
			}
			System.out.print("\n");
		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
