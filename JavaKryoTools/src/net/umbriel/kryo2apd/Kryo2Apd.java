package net.umbriel.kryo2apd;

/**
 *  (c) 2018 Daniel Jameson
 *     
 *  This program is free software: you can redistribute it and/or modify
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

import java.io.File;

import net.umbriel.kryolib.*;
import org.apache.commons.cli.*;

public class Kryo2Apd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length==0) {
			printUsage();
			System.exit(0);
		}
		Boolean omitFM = false;




		// create Options object
		Options options = new Options();

		// add Options
		options.addOption("h", false, "print this message");
		options.addOption("f", false, "omit FM scan");

		// Parse commandline
		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse( options, args);
			if (cmd.hasOption("h")) {
				printOptions(options);
			} else {
				if (cmd.hasOption("f")) {
					omitFM=true;
				}
				if (cmd.getArgs().length==0) {
					printUsage();
				} else {
					File kryoDir = new File(cmd.getArgs()[0]);
					APDWriter.createAPD(kryoDir,omitFM);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


	}

	static void printUsage() {
		System.out.println("Usage: kryo2apd [options] [kryofluxdir]");
	}

	static void printOptions(Options o) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "kryo2apd [options] [kryofluxdir]", o );

	}

}
