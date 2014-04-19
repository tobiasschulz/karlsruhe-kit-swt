package de.uka.stud.uaeti.farbklassifikation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * This class represents a Mosaik. It contains the file name of the image file and the average color and it is
 * serializable and comparable (by comparing the file).
 * 
 * @author tobias
 */
public class Mosaik implements Comparable<Object>, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7604514978316558000L;

	/**
	 * the file which contains the image
	 */
	private File imageFile;

	/**
	 * the average color
	 */
	private Color averageColor;

	/**
	 * Constructs a Mosaik object. It throws IOException if the given file can't be read.
	 * 
	 * @param imageFile
	 *            the given file
	 * @throws IOException
	 *             if the file can't be read
	 */
	public Mosaik(File imageFile) throws IOException {
		if (imageFile == null) {
			throw new IllegalArgumentException("File imageFile must not be null.");
		}

		this.imageFile = imageFile;
		final BufferedImage image = this.getImage();
		this.averageColor = Mosaik.getAverageColor(image);
	}

	private BufferedImage getImage() throws IOException {
		// read the image from the file
		ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
		ImageReader reader = readers.next();
		reader.setInput(iis, true);
		BufferedImage image = reader.read(0, null);
		return image;
	}

	/**
	 * compute the average color for red, green and blue from all pixels of {@code image}.
	 * 
	 * @param image
	 *            the image
	 * @return the average color as Color object.
	 */
	public static Color getAverageColor(final BufferedImage image) {
		if (image == null) {
			throw new IllegalArgumentException("BufferedImage image must not be null.");
		}

		// the average colors
		int red = 0;
		int green = 0;
		int blue = 0;

		// for each pixel...
		for (int y = 0; y < image.getHeight(); ++y) {
			for (int x = 0; x < image.getWidth(); ++x) {
				// create a Color object
				Color pixel = new Color(image.getRGB(x, y));
				// extract red/green/blue
				red += pixel.getRed();
				green += pixel.getGreen();
				blue += pixel.getBlue();
			}
		}

		// divide by the count of pixels
		final int countOfPixels = image.getHeight() * image.getWidth();
		red /= countOfPixels;
		green /= countOfPixels;
		blue /= countOfPixels;

		return new Color(red, green, blue);
	}

	/**
	 * get the file object.
	 * 
	 * @return the file object
	 */
	public File getImageFile() {
		return imageFile;
	}

	/**
	 * get the average color.
	 * 
	 * @return the average color
	 */
	public Color getAverageColor() {
		return averageColor;
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof Mosaik) {
			return this.imageFile.compareTo(((Mosaik) arg0).imageFile);
		}
		return 0;
	}

	@Override
	public String toString() {
		return Util.toHexString(averageColor.getRed()) + " " + Util.toHexString(averageColor.getGreen()) + " "
				+ Util.toHexString(averageColor.getBlue()) + " " + this.imageFile;

	}
}
