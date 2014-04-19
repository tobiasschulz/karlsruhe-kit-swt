package edu.kit.ipd.Mosaique;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Berechnet für ein Bild den Durchschnittswert der drei Farbwerte über alle
 * Pixel.
 * 
 * @author Thomas Karcher
 */
public class Mosaique {
	private Configuration config;
	private ClassifiedDirectory myClassifiedDirectory;

	/**
	 * 
	 * @param configuration
	 *            The command line configuration (as read by args4j).
	 */
	public Mosaique(Configuration configuration) {
		config = configuration;
		myClassifiedDirectory = null;
	}

	/**
	 * 
	 * Reads the directory given by the command line configuration and produces
	 * output.
	 */
	public void readDirectory() {
		System.out.println("Read directory: " + config.getMosaiqueSource());
		myClassifiedDirectory = new ClassifiedDirectory(
				config.getMosaiqueSource());
		myClassifiedDirectory.printFilesAndColors();

		if (config.getInfo() != null && config.getInfo().canWrite()) {
			System.out.println("Serialize: " + config.getInfo());
			/* save results to file */
			FileOutputStream fos = null;
			ObjectOutputStream out = null;
			try {
				fos = new FileOutputStream(config.getInfo());
				out = new ObjectOutputStream(fos);
				out.writeObject(myClassifiedDirectory);
				out.close();
			} catch (IOException ex) {
				System.out.println("Fehler beim Schreiben der Ergebnisdatei.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Reads a previously written result file and prints it on the command line.
	 */
	public void readResultFile() {
		FileInputStream fis;
		ObjectInputStream in = null;
		try {
			System.out.println("Deserialize: " + config.getInfo());
			// Einlesen des zuvor gespeicherten Datenstroms
			fis = new FileInputStream(config.getInfo());
			in = new ObjectInputStream(fis);
			myClassifiedDirectory = (ClassifiedDirectory) in.readObject();
			in.close();
			myClassifiedDirectory.printFilesAndColors();
		} catch (IOException ex) {
			System.out.println("Fehler beim Lesen der Ergebnisdatei.");
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			System.out.println("Fehler im Dateiformat der Ergebnisdatei.");
			ex.printStackTrace();
		}
	}

	/**
	 * Main-Methode zum Aufruf von der Kommandozeile
	 * 
	 * @param args
	 *            Kommandozeilenargumente
	 */
	public static void main(String[] args) {
		/* Einlesen der Kommandozeilenargumente */
		Configuration config = new Configuration(args);

		/*
		 * Gab es beim Parsen der Kommandozeilenargumente einen Fehler? Dann
		 * hier abbrechen.
		 */
		if (!config.isErrorFree()) {
			System.out.println("Fehler beim Aufruf.");
			System.exit(1);
		}

		Mosaique myClassifier = new Mosaique(config);

		if (config.getMosaiqueSource() != null) {
			myClassifier.readDirectory();
		} else if (config.getInfo() != null) {
			myClassifier.readResultFile();
		}

		if (myClassifier.hasMosaiqueImages()) {
			myClassifier.createMosaique();
		}
	}

	private boolean hasMosaiqueImages() {
		return myClassifiedDirectory != null
				&& myClassifiedDirectory.size() >= 1;
	}

	private void createMosaique() {
		if (!hasMosaiqueImages()) {
			throw new IllegalArgumentException("There are no mosaik images.");
		}

		MosaiqueImage image = new MosaiqueImage(config.getSource(),
				config.getRows(), config.getColumns());
		image.createMosaique(config.getTarget(), myClassifiedDirectory);
	}
}
