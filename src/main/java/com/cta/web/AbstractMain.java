package com.cta.web;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.cta.utils.MyExceptionUtils;

public abstract class AbstractMain {

	protected static Options options = new Options();

	protected static String getOptionValueString(CommandLine cmd, String option, String defaultValue) {
		String result = defaultValue;
		
		if(cmd.hasOption(option)) {
			result = cmd.getOptionValue(option);
		}
		 
		return result;
	}

	protected static int getOptionValueInt(CommandLine cmd, String option, int defaultValue) {
		int result = defaultValue;
		
		if(cmd.hasOption(option)) {
			result = Integer.parseInt(cmd.getOptionValue(option));
		}
		
		return result;
	}

	protected static CommandLine parseCommandLine(String[] args) {
		try {
			CommandLineParser parser = new GnuParser();
			return parser.parse(options, args);
		} catch (ParseException e) {
			displayUsage("BdManagerEmbededServer");
			throw MyExceptionUtils.unhandle(e); 
		}
	}

	protected static void displayUsage(String title) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(title, options);
	}
	
	protected static void displayUsage() {
		displayUsage("Help");
	}
}