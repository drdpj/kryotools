/**
 *     Copyright (C) 2013  Daniel Jameson

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */
package net.umbriel.kryolib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author daniel
 *
 */
public class TrackStreamReader {
	

	public static int NOP1=8;
	public static int NOP2=9;
	public static int NOP3=0xA;
	public static int OVR16=0xB;
	public static int VAL16=0xC;
	public static int OOB=0xD;

	static StreamTrack parseTrack(File f) throws InvalidStreamException {
		StreamTrack track = new StreamTrack();
		ArrayList<Flux> fluxes= track.getFluxes(); // pointer to flux arraylist...
		ArrayList<OOBBlock> oobBlocks = track.getOobBlocks(); // pointer to the OOB Blocks
		ArrayList<Index> indexes = track.getIndexes(); // pointer to the indexes
		Pattern sckPat = Pattern.compile(".*sck\\=(\\d+\\.\\d+),.*");
		Pattern ickPat = Pattern.compile(".*ick\\=(\\d+\\.\\d+).*");

		/*
		 * Do the actual parsing...
		 * The stream consists of two different data types,
		 * flux timings and OOB (out of block) data.
		 * First byte of a decode loop dictates how to process...
		 * 
		 */
		try {
			FileInputStream fis = new FileInputStream(f); // Here we go...
			int readByte = 0;
			int overflowCount =0;
			long streamPos =0;

			while ((readByte = fis.read()) >-1 ) { // if we're not at the end of the file...

				Long fluxValue = (long)0;

				if (readByte < NOP1) { // new 2 byte cell value
					fluxValue = 0x10000 * (long) overflowCount; //deal with overflow
					fluxValue += readByte<<8; //Two byte, big endian...
					fluxValue += fis.read();
					fluxes.add(new Flux(fluxValue, streamPos)); //Store it
					streamPos+=2; //Stream position +2
					overflowCount=0;
				} else if (readByte == NOP1) { //NOP 1 
					streamPos++; //NOPs count as stream position...
				} else if (readByte == NOP2) { //NOP 2
					fis.read(); //skip two bytes
					streamPos+=2; //Stream Position +2
				} else if (readByte == NOP3) { //NOP 3
					fis.read(new byte[2]); //skip three bytes
					streamPos+=3; //Stream Position +=3
				} else if (readByte == OVR16) { //Overflow 16 
					overflowCount++; //flux value+=0x10000
					streamPos++;
				} else if (readByte == VAL16) { //Value16
					fluxValue = 0x10000 * (long) overflowCount; //deal with overflow
					fluxValue += fis.read()<<8; //upper 8 bits
					fluxValue += fis.read(); //lower 8 bits
					fluxes.add(new Flux(fluxValue,streamPos)); //Store it
					streamPos+=3;
					overflowCount=0;
				} else if (readByte == OOB) { //OOB
					OOBBlock newBlock = new OOBBlock(fis.read());
					//Get the size over the next two bytes (little endian)
					newBlock.setStrPos(streamPos);
					Integer size = (fis.read()|fis.read()<<8);
					newBlock.setSize(size);

					ArrayList<Integer> data = new ArrayList<Integer>(); //read the data into here
					for (int i=0; i<size; i++) {
						data.add(fis.read());

					}
					newBlock.setData(data); //set the data
					oobBlocks.add(newBlock); //Add the block
					overflowCount=0; //if overflowCount>0 here there's a problem zero it anyway

				} else if (readByte > OOB) { // new single byte cell value
					fluxes.add(new Flux((long)readByte, streamPos)); //store it
					fluxValue = 0x10000 * (long) overflowCount; //deal with overflow
					streamPos++;
					overflowCount=0;
				}


			}
			fis.close();

			/*
			 * Track is now read in, all OOBs in place. 
			 * Indexes now need to be identified and recorded
			 * and if the header has timing info, that needs to be
			 * added.
			 * 
			 */

			Iterator<OOBBlock> iter = oobBlocks.iterator();
			while (iter.hasNext()) {
				OOBBlock block = iter.next();
				if (block.getType()==OOBBlock.INDEX) { //indexes
					//Data block should be 12 bytes, little-endian...
					Index index = new Index();
					ArrayList<Integer> data = block.getData();
					if (data.size()!=12) throw new InvalidStreamException("Index wrong size");
					//Stream position
					index.setSPos(data.get(0)+(data.get(1)<<8)+(data.get(2)<<16)+(data.get(3)<<24));
					//SCK
					index.setSysTime(data.get(4)+(data.get(5)<<8)+(data.get(6)<<16)+(data.get(7)<<24));
					//ICK
					index.setTimer(data.get(8)+(data.get(9)<<8)+(data.get(10)<<16)+(data.get(11)<<24));
					indexes.add(index); //add it to the track...

				} else if (block.getType()==OOBBlock.INFO) { //infoblock
					StringBuffer info = new StringBuffer();
					Iterator<Integer> iter2 = block.getData().iterator();
					while (iter2.hasNext()) {
						info.append((char)iter2.next().intValue());
					}
					track.setInfoText(info.toString());

					/*
					 * Parse out sck and ick - could be one regexp, but no guarantee
					 * format of this block will remain the same...
					 */
					Matcher match = sckPat.matcher(info);
					if (match.matches()) {
						track.setSampleClock(Double.parseDouble(match.group(1)));
					}
					match = ickPat.matcher(info);
					if (match.matches()) {
						track.setIndexClock(Double.parseDouble(match.group(1)));
					}

					/**
					 * The positions recorded in the block data for streamread and streamend
					 * blocks should match with where they appear in the stream. These
					 * form a check firstly for my code being correct and secondly that
					 * all the data that should be in the stream is present. These next
					 * two conditional clauses catch that situation.  The stream position
					 * encoded in index blocks is different. It relates to the position in
					 * the stream buffer of the NEXT flux reversal after the index was 
					 * detected.
					 */

				} else if (block.getType()==OOBBlock.READ) {
					ArrayList<Integer> data = block.getData();
					long position=(data.get(0)+(data.get(1)<<8)+(data.get(2)<<16)+(data.get(3)<<24));
					if (position!=block.getStrPos()) {
						throw new InvalidStreamException("Data missing in stream.");
					}

				} else if (block.getType()==OOBBlock.END) {
					ArrayList<Integer> data = block.getData();
					long position=(data.get(0)+(data.get(1)<<8)+(data.get(2)<<16)+(data.get(3)<<24));
					if (position!=block.getStrPos()) {
						throw new InvalidStreamException("Data missing in stream.");
					}
				}

			}

			// Give the indexes a position in the flux stream...
			// Not using iterators as they're much slower...
			// Also calculate average RPM...
	
			int fluxCount=0;
			for (int i=0; i<indexes.size(); i++) {
				Index currentIndex = indexes.get(i);
				long position = currentIndex.getSPos();
				while (fluxes.get(fluxCount).getStreamPos()<position) {

						fluxCount++;
						if (fluxCount==fluxes.size()) {
							break;
						}
				}
				currentIndex.setFluxIndex(fluxCount);

			}
			

			// Sort out the flux positions in microseconds as easier to deal with
			double clock = track.getSampleClock();
			for (int i=0; i<fluxes.size(); i++) {
				fluxes.get(i).setNanoSecondTime(clock);
			}
			
			//System.out.println(track.getInfoText());
			


		} catch (IOException e) {

			e.printStackTrace();
		}


		return track;
	}

	
}
