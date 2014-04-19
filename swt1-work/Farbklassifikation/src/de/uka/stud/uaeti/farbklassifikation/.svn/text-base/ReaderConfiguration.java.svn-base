package de.uka.stud.uaeti.farbklassifikation;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;

/**
 * This class handles the program arguments.
 * 
 * @author Thomas Karcher
 * 
 */
public class ReaderConfiguration {

	@Option(name = "-s", aliases = { "--source" }, required = true, usage = "Sets the file where the input is read from.")
	private File source;

	private boolean errorFree = false;

	/**
	 * Parses the command line arguments using <a href="http://args4j.java.net/">args4j</a> .
	 * 
	 * @param args
	 *            The program arguments:
	 *            <ul>
	 *            <li>-s (--source) FILE</li>
	 *            </ul>
	 */
	public ReaderConfiguration(String... args) {
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);
		try {
			parser.parseArgument(args);

			if (getSource().isDirectory() || (getSource().exists() && !getSource().canWrite())) {
				throw new CmdLineException(parser, "--source ist keine gueltige Datei");
			}

			errorFree = true;
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println("java Reader [options...]");
			parser.printUsage(System.err);
			System.err.println();
			System.err.println("Example: java Reader" + parser.printExample(ExampleMode.ALL));
		}
	}

	/**
	 * Returns whether the parameters could be parsed without an error.
	 * 
	 * @return <code>true</code> if no error occurred.
	 */
	public boolean isErrorFree() {
		return errorFree;
	}

	/**
	 * Returns the source file.
	 * 
	 * @return The source file.
	 */
	public File getSource() {
		return source;
	}
}
