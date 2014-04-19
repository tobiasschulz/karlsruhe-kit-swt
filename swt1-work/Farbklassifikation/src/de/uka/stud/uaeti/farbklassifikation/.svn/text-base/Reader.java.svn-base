package de.uka.stud.uaeti.farbklassifikation;

import java.io.File;
import java.io.IOException;

/**
 * The main class. It reads a serialized data structure containing a list of image files and their average colors from a
 * file and prints it to the standard output.
 * 
 * @author tobias
 */
public final class Reader {

	/**
	 * Should not be instantiated.
	 */
	private Reader() {
	}

	/**
	 * @param args
	 *            the commandline arguments
	 */
	public static void main(String[] args) {
		ReaderConfiguration conf = new ReaderConfiguration(args);
		// exit if no or invalid arguments are given
		if (!conf.isErrorFree()) {
			return;
		}

		// construct the data structure for files and average colors
		MosaikFiles files = null;

		// read the serialized data from the given file
		File sourceFile = conf.getSource();
		try {
			files = MosaikFiles.readFrom(sourceFile);

			// print the image files and their average colors
			files.printAll();

		} catch (IOException e) {
			// source file not found or invalid
			e.printStackTrace();
		}
	}

}
