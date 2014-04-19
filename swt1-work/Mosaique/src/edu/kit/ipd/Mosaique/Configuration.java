package edu.kit.ipd.Mosaique;

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
public class Configuration {

	@Option(name = "-s", aliases = { "--source" }, required = false, usage = "Sets "
			+ "the file where the original image is read from.")
	private File source;

	@Option(name = "-i", aliases = { "--info" }, required = false, usage = "Sets "
			+ "the file where the serialized information about the mosaik files is written to.")
	private File info;

	@Option(name = "-m", aliases = { "--mosaiquesource" }, required = false, usage = "Sets "
			+ "the directory where the mosaik input files are read from.")
	private File mosaiquesource;

	@Option(name = "-r", aliases = { "--rows" }, required = false, usage = "Sets the count of rows.")
	private int rows;

	@Option(name = "-c", aliases = { "--columns" }, required = false, usage = "Sets the count of columns.")
	private int columns;

	@Option(name = "-d", aliases = { "--dest" }, required = false, usage = "Sets the file where "
			+ "the mosaik output image is written to.")
	private File target;

	private boolean errorFree = false;

	/**
	 * Parses the command line arguments using <a
	 * href="http://args4j.java.net/">args4j</a> .
	 * 
	 * @param args
	 *            The program arguments:
	 *            <ul>
	 *            <li>-s (--source) FILE</li>
	 *            <li>-i (--info) FILE</li>
	 *            <li>-m (--mosaiquesource) DIRECTORY</li>
	 *            <li>-r (--rows) INTEGER</li>
	 *            <li>-c (--columns) INTEGER</li>
	 *            <li>-d (--dest) FILE</li>
	 *            </ul>
	 */
	public Configuration(String... args) {
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);
		try {
			parser.parseArgument(args);

			if (getSource() != null && getSource().exists()
					&& !getSource().canRead()) {
				throw new CmdLineException(parser,
						"--source ist keine gültige Datei");
			}
			if (getTarget() != null
					&& (getTarget().isDirectory() && !getTarget().canWrite())) {
				throw new CmdLineException(parser,
						"--dest ist keine gültige Datei");
			}

			if (getRows() == 0) {
				throw new CmdLineException(parser,
						"--rows ist keine gültige Ganzzahl");
			}
			if (getColumns() == 0) {
				throw new CmdLineException(parser,
						"--columns ist keine gültige Ganzzahl");
			}

			boolean mosaiqueDirectoryGiven = getMosaiqueSource() != null
					&& getMosaiqueSource().isDirectory();
			boolean infoFileReadable = getInfo() != null && getInfo().exists()
					&& getInfo().canRead();

			if (!mosaiqueDirectoryGiven && !infoFileReadable) {
				throw new CmdLineException(parser,
						"--info oder --mosaiquesource muss angegeben und gültig sein (oder beide)");
			}

			errorFree = true;
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println("java -jar Mosaique.jar [options...]");
			parser.printUsage(System.err);
			System.err.println();
			System.err.println("Example: java -jar Mosaique.jar"
					+ parser.printExample(ExampleMode.ALL));
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
	 * Returns the source directory.
	 * 
	 * @return The source directory.
	 */
	public File getMosaiqueSource() {
		return mosaiquesource;
	}

	/**
	 * Returns the serialized information file.
	 * 
	 * @return The serialized information file.
	 */
	public File getInfo() {
		return info;
	}

	/**
	 * Returns the rows.
	 * 
	 * @return The rows.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Returns the columns.
	 * 
	 * @return The columns.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Returns the target file.
	 * 
	 * @return The target file.
	 */
	public File getTarget() {
		return target;
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
