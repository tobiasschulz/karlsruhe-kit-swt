package de.uka.stud.uaeti.farbklassifikation;

import java.io.File;

/**
 * The main class. It reads image files from a specified directory, prints a list of all files and their average colors
 * and writes the data structure in a file.
 * 
 * @author tobias
 */
public final class Main {

	/**
	 * Should not be instantiated.
	 */
	private Main() {
	}

	/**
	 * @param args
	 *            the commandline arguments
	 */
	public static void main(String[] args) {
		MainConfiguration conf = new MainConfiguration(args);
		// exit if no or invalid arguments are given
		if (!conf.isErrorFree()) {
			return;
		}

		// construct the data structure for files and average colors
		MosaikFiles files = new MosaikFiles();

		// read the files in the given directory
		File sourceDir = conf.getSource();
		files.readFiles(sourceDir);

		// the output file for serialization
		File targetFile = conf.getTarget();
		MosaikFiles.writeTo(targetFile, files);

		// print the files and their average colors
		files.printAll();
	}

}
