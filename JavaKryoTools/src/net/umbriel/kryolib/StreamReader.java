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

import java.io.*;
import java.util.regex.*;
import java.util.*;
import net.umbriel.kryolib.exceptions.InvalidStreamException;

/**
 * @author daniel
 * Generates a kryoflux stream object from a valid stream directory
 *
 */
public class StreamReader {
	
	public static int NOP1=8;
	public static int NOP2=9;
	public static int NOP3=0xA;
	public static int OVR16=0xB;
	public static int VAL16=0xC;
	public static int OOB=0xD;

	private File directory;
	private Stream parsedStream;

	
	/**
	 * @param d Directory containing kryoflux stream
	 */
	public StreamReader(File d) throws InvalidStreamException {

		setDirectory(d);
		// Check if it's a directory
		if (!directory.isDirectory()) throw new InvalidStreamException("Not a directory.");
		// Check if at least one track file is within directory...
		File check = new File(directory,"track00.0.raw");
		if (!check.exists()) throw new InvalidStreamException("No stream found.");
		try {
			processStream();
		} catch (InvalidStreamException e) { 
			throw e; //chuck upwards
		}
	}

	private void processStream() throws InvalidStreamException {
		Pattern trackPattern = Pattern.compile("track(\\d{2})\\.(\\d)\\.raw");
		parsedStream = new Stream(); //Somewhere to store all of this
		File[] files = directory.listFiles(); // get the files
		Integer sides = 0; // only side 0 initially...
		Integer tracks = Integer.MIN_VALUE;

		/*
		 * From the files array we want to get the maximum tracks
		 * and the maximum sides..
		 *  
		 */
				
		for (int i=0; i< files.length; i++) {
			Matcher trackMatch = trackPattern.matcher(files[i].getName());
			if (trackMatch.matches()) {
				if (trackMatch.group(2).equals("1")) sides = 1; // double sided
				tracks = Math.max(Integer.parseInt(trackMatch.group(1)),tracks);
			}
		}
		
		// Set up the loop to process the disk
		
		for (int i=0; i<tracks+1; i++) { //tracks
			for (int j=0; j<sides+1; j++) { //sides
				String trackName = "track"+String.format("%02d", i)+"."+j+".raw";
				if (Utils.DEBUG) {
					System.out.println("Processing "+trackName);
				}
				File currentFile = 
						new File(directory.getPath(),trackName);
				parsedStream.getTracks().add(parseTrack(currentFile));
				
			}

		}
		
		
	}

	private StreamTrack parseTrack(File f) {
		StreamTrack track = new StreamTrack();
		ArrayList<Long> fluxes= track.getFluxes(); // pointer to flux arraylist...
		ArrayList<OOBBlock> oobBlocks = track.getOobBlocks(); // pointer to the OOB Blocks
		
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
			
			while ((readByte = fis.read()) >-1 ) { // if we're not at the end of the file...
				Long fluxValue = (long)0;
				
				if (readByte < NOP1) { // new 2 byte cell value
					fluxValue = 0x10000 * (long) overflowCount; //deal with overflow
					fluxValue += readByte<<8; //Two byte, big endian...
					fluxValue += fis.read();
					fluxes.add(fluxValue); //Store it
					overflowCount=0;
				} else if (readByte == NOP1) { //NOP 1
					fis.read(); //skip a byte
				} else if (readByte == NOP2) { //NOP 2
					fis.read(new byte[2]); //skip two bytes
				} else if (readByte == NOP3) { //NOP 3
					fis.read(new byte[3]); //skip three bytes
				} else if (readByte == OVR16) { //Overflow 16 
					overflowCount++; //flux value+=0x10000
				} else if (readByte == VAL16) { //Value16
					fluxValue = 0x10000 * (long) overflowCount; //deal with overflow
					fluxValue += fis.read()<<8; //upper 8 bits
					fluxValue += fis.read(); //lower 8 bits
					fluxes.add(fluxValue); //Store it
					overflowCount=0;
				} else if (readByte == OOB) { //OOB
					OOBBlock newBlock = new OOBBlock(fis.read());
					//Get the size over the next two bytes (little endian)
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
					fluxes.add((long)readByte); //store it
					fluxValue = 0x10000 * (long) overflowCount; //deal with overflow
					overflowCount=0;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return track;
	}

	private void setDirectory(File directory) {
		this.directory = directory;
	}

	/**
	 * @return the parsed stream
	 */
	public Stream getParsedStream() {
		return parsedStream;
	}


}
