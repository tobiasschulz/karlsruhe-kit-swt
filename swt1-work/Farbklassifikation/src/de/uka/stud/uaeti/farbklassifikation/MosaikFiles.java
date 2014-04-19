package de.uka.stud.uaeti.farbklassifikation;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a list of Mosaik objects.
 * 
 * @author tobias
 */
public class MosaikFiles implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5368472410122522637L;

	/** a hashmap containing all files and their average colors */
	private HashMap<Color, HashSet<Mosaik>> map = new HashMap<Color, HashSet<Mosaik>>();

	/**
	 * Returns a list of mosaik images which have the given average color.
	 * 
	 * @param c
	 *            the given average color
	 * @return the list of mosaik images
	 */
	public Mosaik[] byColor(final Color c) {
		if (c == null) {
			throw new IllegalArgumentException("Color must not be null.");
		}
		return (Mosaik[]) map.get(c).toArray();
	}

	/**
	 * Loads a given file into this data structure by creating an Mosaik object.
	 * 
	 * @param sourceFile
	 *            the given image file
	 * @throws IOException
	 *             when the file can't be read
	 */
	public void readFile(File sourceFile) throws IOException {
		if (sourceFile == null) {
			throw new IllegalArgumentException("Source file must not be null.");
		}

		// create a new mosaik object from the given image file
		Mosaik mosaik = new Mosaik(sourceFile);

		// get the average color
		Color avgColor = mosaik.getAverageColor();

		// add it to the hash map
		if (!map.containsKey(avgColor)) {
			map.put(avgColor, new HashSet<Mosaik>());
		}
		HashSet<Mosaik> mosaiks = map.get(avgColor);
		mosaiks.add(mosaik);
		map.put(avgColor, mosaiks);
	}

	/**
	 * Loads all image files from the given directory to this data structure. Non-image files, currupt image files and
	 * other IO Errors are ignored.
	 * 
	 * @param sourceDir
	 *            the given source directory
	 */
	public void readFiles(File sourceDir) {
		if (sourceDir == null) {
			throw new IllegalArgumentException("Source directory must not be null.");
		}

		// for each file...
		for (File sourceFile : sourceDir.listFiles()) {
			try {
				// read the file and compute it's average color
				this.readFile(sourceFile);
			} catch (IOException e) {
				// ignore it if the file is not found/invalid/...
				continue;
			}
		}
	}

	/**
	 * Serialize the given MosaikFiles object and write it info the given file.
	 * 
	 * @param targetFile
	 *            the output file
	 * @param files
	 *            the object to serialize
	 */
	public static void writeTo(File targetFile, MosaikFiles files) {
		if (targetFile == null) {
			throw new IllegalArgumentException("Target file must not be null.");
		} else if (files == null) {
			throw new IllegalArgumentException("Mosaik file list must not be null.");
		}

		OutputStream fos = null;

		try {
			fos = new FileOutputStream(targetFile);
			ObjectOutputStream o = new ObjectOutputStream(fos);
			o.writeObject(files);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Read a serizalized MosaikFiles object from the given file. Throws an IOException if the file can't be read.
	 * 
	 * @param sourceFile
	 *            the source file
	 * @return the deserialized MosaikFiles object
	 * @throws IOException
	 *             if the file can't be read
	 */
	public static MosaikFiles readFrom(File sourceFile) throws IOException {
		if (sourceFile == null) {
			throw new IllegalArgumentException("Source file must not be null.");
		}
		
		InputStream file = new FileInputStream(sourceFile);
		InputStream buffer = new BufferedInputStream(file);
		ObjectInput input = new ObjectInputStream(buffer);

		MosaikFiles files = null;
		try {
			// deserialize
			files = (MosaikFiles) input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
		return files;
	}

	/**
	 * prints all image files and their average color seperated by the red, green, blue values sorted by their filename.
	 */
	public void printAll() {
		ArrayList<Mosaik> mosaiks = new ArrayList<Mosaik>();
		for (final HashSet<Mosaik> list : map.values()) {
			mosaiks.addAll(list);
		}
		Collections.sort(mosaiks);
		for (final Mosaik m : mosaiks) {
			System.out.println(m);
		}
	}
}
