package net.umbriel.kryolib;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.GZIPOutputStream;

/**
 * 
 * @author daniel
 *
 */


/**
 * 
  	APD Format (From Jon Abbott):
  	- The APD file is a GZip of an original APD file
	- Compressed file always starts:
      1F 8B 08 00 00 00 00 00 00 0B EC BD
      ^^ ^^ ^^ ^^ ^^ ^^ ^^ ^^ ^^ ^^
      |  |  |  |  |  |  |  |  |  |
      |  |  |  |  |  |  |  |  |  +- OS
      |  |  |  |  |  |  |  |  +---- xfl
      |  |  |  |  +--+--+--+------- time
      |  |  |  +------------------- gzip flags
      |  |  +---------------------- gzip compression*
      |  |
      +--+------------------------- gzip header

 	*  Compression method: 8 is the only supported format

	Original APD file structure:
           0 - 7   "APDX0001" identifier
           8 - B   t0sd - Track 0 SD length in bits
           C - F   t0dd - Track 0 DD length in bits
          10 - 13  t0qd - Track 0 QD length in bits
          14 - 1F  t1sd - Track 1 SD length in bits
          20 - 23  t1dd - Track 1 DD length in bits
          24 - 27  t1qd - Track 1 QD length in bits
	...                repeated to Track 159
        77C - 787  Track 160 (blank)
        7C4 - 7CF  Track 166 (blank)

              	  7D0  	Track 0 SD data
	+ (t0sd + 7) >> 3 	Track 0 DD data
	+ (t0dd + 7) >> 3  	Track 0 QD data

	+ (t0qd + 7) >> 3  	Track 1 SD data
	+ (t1sd + 7) >> 3  	Track 1 DD data
	+ (t1dd + 7) >> 3  	Track 1 QD data

	SD    data is big-endian raw FM words
	DD/QD data is big-endian raw MFM words

	As far as I can tell, the tracks are always sequential, so
	physical tracks translate as:

	Physical         APD
	--------------   -------
	Side 0 Track 0 > Track 0
	Side 1 Track 0 > Track 1
	Side 0 Track 1 > Track 2
	etc.

 *
 */

public final class APDWriter {

	public static void createAPD(File f) {

		//Some things we need:
		String mfmTrackMark = "0"+Integer.toBinaryString(0x4489); //Need to stick on the preceding 0
		mfmTrackMark = mfmTrackMark+mfmTrackMark; //0x44894489
		String fmTrackMark = "1111010101111110"; //0xFFFFAAAA
		//String fmTrackMark = Integer.toBinaryString(0xFF)+Integer.toBinaryString(0xAAAAAA)+Integer.toBinaryString(0xAAAAAA); 

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
			StreamReader sr = new StreamReader(f); //Create the stream reader
			int maxTracks = sr.getNumberOfTracks();
			int maxSides = sr.getNumberOfSides();
			int arraySize = maxTracks*(maxSides);

			//Places for FM, MFM and HD tracks...
			byte[][] fmTracks = new byte[arraySize][];
			byte[][] mfmTracks = new byte[arraySize][];
			byte[][] hdMfmTracks = new byte[arraySize][];
			for (int track=0; track<maxTracks; track++) { //for each track
				for (int side=0; side<maxSides; side++) { //for each side


					int currentTrack = track*2 + side;
					//We try fm,mfm then quad density if that way inclined...
					StreamTrack st = sr.getTrack(track, side); //got the track...
					System.out.println("Track:"+track+" Side:"+side+" APD Track:"+currentTrack);
					fdc.setClockCentre(4000); //set the clock for SD (4000ns)
					fdc.setTrack(st); //set and parse the track

					String trackBitString = fdc.getBinaryString();
					int fmIndex=trackBitString.indexOf(fmTrackMark); //look for FM track index

					if (fmIndex!=-1) {
						//deal with the fm string

						fmTracks[currentTrack]=fdc.getTrackByteArray(0);
						int length=fmTracks[currentTrack].length*8;
						System.out.println("FM track found:"+fmIndex+" Length:"+length);
						int headerPos =8+(12*currentTrack); //Position in the header for the tracklength
						header=updateHeader(header, headerPos, length); //update the header

					} 
					fdc.setClockCentre(2000); //set for DD
					fdc.processTrack(); //reprocess
					trackBitString=fdc.getBinaryString();
					int mfmIndex=trackBitString.indexOf(mfmTrackMark); //look for MFM track index
					if (mfmIndex!=-1) {
						//deal with mfm string

						mfmTracks[currentTrack]=fdc.getTrackByteArray(0);
						int length = mfmTracks[currentTrack].length*8;
						System.out.println("MFM track found:"+mfmIndex+" Length:"+length);
						int headerPos =12+(12*currentTrack); //Position in the header for the tracklength
						header=updateHeader(header, headerPos, length); //update the header
					} 


					//quad density...
					fdc.setClockCentre(1000); //Set the clock for HD (1000ns window)
					fdc.processTrack(); //Reprocess the track
					trackBitString=fdc.getBinaryString();
					mfmIndex=trackBitString.indexOf(mfmTrackMark); //look for MFM track...
					if (mfmIndex !=-1) { //If we find one...
						//deal with HD mfm...

						hdMfmTracks[currentTrack]=fdc.getTrackByteArray(0);
						int length=hdMfmTracks[currentTrack].length*8; 
						System.out.println("HD MFM track found:"+mfmIndex+" Length:"+length);
						int headerPos =16+(12*currentTrack); //Position in the header for the tracklength
						header=updateHeader(header, headerPos, length);
					} 



				}
			}
			//Job done, output the apd
			FileOutputStream fo = new FileOutputStream(f.getName()+".apd");
			GZIPOutputStream go = new GZIPOutputStream(fo);

			//Stream header
			go.write(header);
			//Stream tracks
			for (int i=0; i<fmTracks.length;i++) {
				//fm
				if (fmTracks[i]!=null) {
					go.write(fmTracks[i]);
				}
				//mfm
				if (mfmTracks[i]!=null) {
					go.write(mfmTracks[i]);
				}
				//hd
				if (hdMfmTracks[i]!=null) {
					go.write(hdMfmTracks[i]);
				}
			}
			go.flush();
			go.close();


		} catch (InvalidStreamException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static byte[] updateHeader(byte[] h, int position, int length) {
		h[position]=(byte)length;
		h[position+1]=(byte) (length>>>8);
		h[position+2]=(byte) (length>>>16);
		h[position+3]=(byte) (length>>>24);
		return h;
	}


}
