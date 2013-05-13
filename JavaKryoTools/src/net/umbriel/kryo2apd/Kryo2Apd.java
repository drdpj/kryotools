package net.umbriel.kryo2apd;

import net.umbriel.kryolib.*;
import org.apache.commons.cli.*;

public class Kryo2Apd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// create Options object
		Options options = new Options();

		// add Options
		options.addOption("t", false, "display current time");
		
		// Parse commandline
		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse( options, args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
