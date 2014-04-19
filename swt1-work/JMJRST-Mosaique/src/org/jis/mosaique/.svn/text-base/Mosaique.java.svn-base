package org.jis.mosaique;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

/**
 * Builds a photo mosaique image
 * 
 * @author Thomas Karcher
 */
public class Mosaique {
	/**
	 * Holds the configuration coming from command line arguments
	 */
	private Configuration config;

	/**
	 * The collection of images (within a directory)
	 */
	private ClassifiedDirectory myClassifiedDirectory;

	/**
	 * The original file (that is to be "mosaiquized")
	 */
	private BufferedImage original;

	/**
	 * The number of tile rows
	 */
	private int numRows;

	/**
	 * The number of tile columns
	 */
	private int numCols;

	/**
	 * The size of one tile in pixel
	 */
	private int tileSize;

	/**
	 * Ctor taking the configuration from the command line
	 * 
	 * @param configuration
	 *            The command line configuration (as read by args4j).
	 */
	public Mosaique(Configuration configuration) {
		config = configuration;
	}

	/**
	 * Main method that steers the process.
	 * 
	 * @throws IOException
	 *             When some file could not be read or written to
	 * @throws ClassNotFoundException
	 *             When the de-serialization went wrong
	 */
	public void process() throws IOException, ClassNotFoundException {
		// read the original file and validate the parameters
		checkConfig();

		// decide whether to read all the PNG files first or use the previously
		// written "info" file.
		if (config.getMosaiquesource() != null) {
			// process PNG files
			readDirectory();

			if (config.getInfo() != null) {
				// serialize the information about the mosaique tiles
				saveInfo();
			}

		} else if (config.getInfo() != null) {
			// read the previously serialized information about the mosaique
			// tiles
			readInfo();
		}

		// Now we're ready to split the original files into tiles and replace
		// them
		for (int c = 0; c < numCols; c++) {
			for (int r = 0; r < numRows; r++) {
				BufferedImage myTile = original.getSubimage(c * tileSize, r
						* tileSize, tileSize, tileSize);
				Color myTileAvgColor = ClassifiedImage
						.calculateColorAvg(myTile);

				ClassifiedImage myFittingCI = myClassifiedDirectory
						.getFittingTile(myTileAvgColor);

				BufferedImage myFittingTile = ClassifiedImage.resize(
						ImageIO.read(new File(myFittingCI.getFilename())),
						tileSize, tileSize, original.getType());

				myTile.setData(myFittingTile.getRaster());
			}
		}

		writeDest();
	}

	/**
	 * Checks whether the command line arguments are sane enough to work with
	 * 
	 * @throws IOException
	 */
	private void checkConfig() throws IOException {
		// read original file
		try {
			original = ImageIO.read(config.getSource());
		} catch (IOException e) {
			System.out.println("Could not read " + config.getSource() + ".");
			throw e;
		}

		int rowPixel = original.getHeight();
		int colPixel = original.getWidth();
		tileSize = config.getKachelSize();
		numRows = rowPixel / tileSize;
		numCols = colPixel / tileSize;
		if (numRows < 2 && numCols >= 2) {
			tileSize = rowPixel / numRows;
			numRows = rowPixel / tileSize;
			numCols = colPixel / tileSize;
		}
		if (numRows >= 2 && numCols < 2) {
			tileSize = rowPixel / numCols;
			numRows = rowPixel / tileSize;
			numCols = colPixel / tileSize;
		}

		if (tileSize < Preview.MIN_KACHEL_SIZE) {
			throw new IllegalArgumentException(
					"Tiles are getting too small (below "
							+ Preview.MIN_KACHEL_SIZE + " pixel).");
		}
	}

	/**
	 * Reads the directory given by the command line configuration.
	 */
	private void readDirectory() {
		myClassifiedDirectory = new ClassifiedDirectory(
				config.getMosaiquesource());
	}

	/**
	 * @throws IOException
	 *             If an error occurs while writing the serialization file
	 */
	private void saveInfo() throws IOException {
		/* save results to file */
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(config.getInfo());
			out = new ObjectOutputStream(fos);
			out.writeObject(myClassifiedDirectory);
			out.close();
		} catch (IOException e) {
			System.out.println("Error writing info file.");
			throw e;
		}

	}

	/**
	 * Reads a previously written result file and prints it on the command line.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readInfo() throws IOException, ClassNotFoundException {
		FileInputStream fis;
		ObjectInputStream in = null;
		try {
			// Einlesen des zuvor gespeicherten Datenstroms
			fis = new FileInputStream(config.getInfo());
			in = new ObjectInputStream(fis);
			myClassifiedDirectory = (ClassifiedDirectory) in.readObject();
			in.close();
			myClassifiedDirectory.printFilesAndColors();
		} catch (IOException e) {
			System.out.println("Error reading info file.");
			throw e;
		} catch (ClassNotFoundException e) {
			System.out.println("Error in info file format.");
			throw e;
		}
	}

	/**
	 * writes the new photo mosaique image
	 */
	private void writeDest() throws IOException {
		ImageIO.write(original, "png", config.getDest());
	}
}
