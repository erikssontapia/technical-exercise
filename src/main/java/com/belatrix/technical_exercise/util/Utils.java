package com.belatrix.technical_exercise.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

/**
 * 
 * @author Eriksson Tapia
 *
 */
public class Utils {

	/**
	 * Method for get File Handler
	 * @param logfilePath
	 * @return
	 */
	public static FileHandler getFileHandler(String logfilePath) {
		FileHandler fh = null;

		try {
			File logFile = new File(logfilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			fh = new FileHandler(logfilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fh;
	}

	/**
	 * Method for get console handler
	 * @return
	 */
	public static ConsoleHandler getConsoleHandler() {
		return new ConsoleHandler();
	}
}
