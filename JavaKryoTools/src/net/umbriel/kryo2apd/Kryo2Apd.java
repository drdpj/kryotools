package net.umbriel.kryo2apd;

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
		
		
		File kryoDir = new File(args[0]);
		APDWriter.createAPD(kryoDir);
		
		/**
		 * Will add CLO's later...
		
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
		} */

	}
	 
	static void printUsage() {
		System.out.println("Usage: kryo2apd [kryofluxdir]");
	}
	
	static void printOptions() {
		
	}

}
