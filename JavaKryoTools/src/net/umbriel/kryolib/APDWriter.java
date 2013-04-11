package net.umbriel.kryolib;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class APDWriter {

	public static void createAPD(File f) {
		
		//Some things we need:
		String mfmTrackMark = "0"+Integer.toBinaryString(0x4489); //Need to stick on the preceeding 0
		mfmTrackMark = mfmTrackMark+mfmTrackMark; //0x44894489
		String fmTrackMark = Integer.toBinaryString(0xFFFF)+Integer.toBinaryString(0xAAAA); //0xFFFFAAAA

		
		//FDC Emulator
		FDCEmulator fdc = new FDCEmulator();
		
		//Header is 2000 bytes...
		byte[] header = new byte[2000];
		String headerString = "APDX0001";
		byte[]textBytes = headerString.getBytes();
		
		for (int i=0; i<textBytes.length;i++) { //copy that to the right place...
			header[i]=textBytes[i];
		}
		
		
		
		//check stream...
		try {
			StreamReader sr = new StreamReader(f);
			int maxTracks = sr.getNumberOfTracks();
			int maxSides = sr.getNumberOfSides();
			if (maxTracks>79) { //we don't have more than 79 tracks in an APD according to !Structure
				maxTracks=79;
			}
			//Places for FM, MFM and HD tracks...
			byte[][] fmTracks = new byte[maxTracks*maxSides][];
			byte[][] mfmTracks = new byte[maxTracks*maxSides][];
			byte[][] hdMfmTracks = new byte[maxTracks*maxSides][];
			ByteBuffer bb;
			
			for (int track=0; track<=maxTracks; track++) { //for each track
				for (int side=0; side<=maxSides; side++) { //for each side
					int currentTrack = track*2 + side;
					//We try fm,mfm then quad density if that way inclined...
					StreamTrack st = sr.getTrack(track, side); //got the track...
					System.out.println("Track:"+track+" Side:"+side);
					fdc.setClockCentre(2000); //set sd/dd
					fdc.setTrack(st); //set and parse the track
					
					String trackBitString = fdc.getBinaryString();
					int fmIndex=trackBitString.indexOf(fmTrackMark); //look for FM track index
					int mfmIndex=trackBitString.indexOf(mfmTrackMark); //look for MFM track index
					if (fmIndex!=-1) {
						//deal with the fm string
						System.out.println("FM track found");
						fmTracks[currentTrack]=fdc.getTrackByteArray(fmIndex);
						bb=ByteBuffer.allocate(4).putInt(fmTracks[currentTrack].length);
						bb.order(ByteOrder.LITTLE_ENDIAN);
						byte[] lengthBytes = bb.array();
						int headerPos =7+8+(12*currentTrack);
						for (int i=headerPos;i<headerPos+lengthBytes.length; i++) {
							header[i]=lengthBytes[i-headerPos];
						}
					} else {
						//zero this one
					}
					if (mfmIndex!=-1) {
						//deal with mfm string
						System.out.println("MFM track found:"+mfmIndex);
						mfmTracks[currentTrack]=fdc.getTrackByteArray(mfmIndex);
						bb=ByteBuffer.allocate(4).putInt(mfmTracks[currentTrack].length);
						bb.order(ByteOrder.BIG_ENDIAN);
						byte[] lengthBytes = bb.array();
						int headerPos =7+12+(12*currentTrack);
						for (int i=headerPos;i<headerPos+lengthBytes.length; i++) {
							header[i]=lengthBytes[i-headerPos];
						}
					} else {
						//zero this one
					}

					//quad density...
					fdc.setClockCentre(1000);
					fdc.processTrack();
					trackBitString=fdc.getBinaryString();
					mfmIndex=trackBitString.indexOf(mfmTrackMark); //look for HD MFM track...
					if (mfmIndex !=-1) {
						//deal with HD mfm...
						System.out.println("HD MFM track found:"+mfmIndex);
						hdMfmTracks[currentTrack]=fdc.getTrackByteArray(mfmIndex);
						bb=ByteBuffer.allocate(4).putInt(hdMfmTracks[currentTrack].length);
						bb.order(ByteOrder.LITTLE_ENDIAN);
						byte[] lengthBytes = bb.array();
						int headerPos =7+16+(12*currentTrack);
						for (int i=headerPos;i<headerPos+lengthBytes.length; i++) {
							header[i]=lengthBytes[i-headerPos];
						}
					} else {
						
					}
					
					
					
				}
			}
			
		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
