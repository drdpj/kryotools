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
		return null;
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
