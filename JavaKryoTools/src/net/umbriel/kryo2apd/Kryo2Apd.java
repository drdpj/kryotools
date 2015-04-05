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
