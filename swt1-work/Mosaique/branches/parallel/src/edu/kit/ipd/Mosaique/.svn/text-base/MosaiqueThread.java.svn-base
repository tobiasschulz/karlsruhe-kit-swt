package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * This Thread is used to compute the Mosaik image.
 * 
 * @author tobias
 */
public class MosaiqueThread extends Thread {
	private ArrayList<Dimension> dimensions;
	private Mosaique mosaique;

	/**
	 * A cache to avoid that some threads compute the same things. Caches the
	 * reading from disk and the resize method of ClassifiedImage.
	 */
	private static HashMap<String, BufferedImage> cache = new HashMap<String, BufferedImage>();

	/**
	 * The public constructor.
	 * 
	 * @param mosaique
	 *            the Mosaique class to refer to
	 */
	public MosaiqueThread(Mosaique mosaique) {
		this.mosaique = mosaique;
		dimensions = new ArrayList<Dimension>();
	}

	/**
	 * Add work to this thread (before executing it)
	 * 
	 * @param dim
	 *            the tile to compute
	 */
	public void addTileToDo(Dimension dim) {
		dimensions.add(dim);
	}

	/**
	 * Do the work which was done in process() method in the Mosaique class.
	 */
	public void run() {
		for (Dimension dim : dimensions) {
			int c = dim.width;
			int r = dim.height;

			BufferedImage myTile = mosaique.getOriginal().getSubimage(
					c * mosaique.getTileSize(), r * mosaique.getTileSize(),
					mosaique.getTileSize(), mosaique.getTileSize());
			Color myTileAvgColor = ClassifiedImage.calculateColorAvg(myTile);

			ClassifiedImage myFittingCI = mosaique.getClassifiedDirectory()
					.getFittingImage(myTileAvgColor);

			final String path = myFittingCI.getFilename();
			final String key = path + mosaique.getTileSize();
			if (!cache.containsKey(key)) {
				synchronized (key) {
					try {
						cache.put(key, myFittingCI.resize(
								ImageIO.read(new File(path)),
								mosaique.getTileSize(), mosaique.getTileSize(),
								mosaique.getOriginal().getType()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			BufferedImage myFittingTile = cache.get(key);
			System.err.println(path + " " + mosaique.getTileSize());

			myTile.setData(myFittingTile.getRaster());
		}
	}
}
