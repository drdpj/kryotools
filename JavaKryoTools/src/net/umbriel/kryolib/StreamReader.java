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
		String trackName = "track";
		String suffix = ".raw";
		setDirectory(d);
		// Check if it's a directory
		if (!directory.isDirectory()) throw new InvalidStreamException("Not a directory.");
		// Check if at least one track file is within directory...
		File check = new File(directory,trackName+"00.0"+suffix);
		if (!check.exists()) throw new InvalidStreamException("No stream found.");
		try {
			processStream();
		} catch (InvalidStreamException e) { 
			throw e; //chuck upwards
		}
	}

	private void processStream() throws InvalidStreamException {
		parsedStream = new Stream();
		
		
	}


	protected void setDirectory(File directory) {
		this.directory = directory;
	}

	/**
	 * @return the parsed stream
	 */
	public Stream getParsedStream() {
		return parsedStream;
	}


}
