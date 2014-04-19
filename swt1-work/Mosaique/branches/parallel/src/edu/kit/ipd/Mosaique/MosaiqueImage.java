package edu.kit.ipd.Mosaique;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author tk
 * 
 */
public class MosaiqueImage implements Iterable<BufferedImage> {
    /**
     * 
     */
    private BufferedImage myBufferedImage;

    /**
     * The tile size in pixel, for the iterator
     */
    private Integer tileSize = 0;

    /**
     * Getter for the tile size in pixel
     * 
     * @return The pixel count
     */
    public Integer getTileSize() {
        return tileSize;
    }

    /**
     * Sets the tile size to build the iterator. Automatically adjusts to the
     * best fitting tile size.
     * 
     * @param tileSize
     *            The tile size in pixel.
     */
    public void setTileSize(Integer tileSize) {
        
        this.tileSize = tileSize;
    }

    /**
     * Ctor, taking the image to build tiles on
     * 
     * @param bi
     *            The image
     */
    public MosaiqueImage(BufferedImage bi) {
        myBufferedImage = bi;
    }

    /**
     * Returning the iterator to walk over the image tile-wise
     * 
     * @return The iterator
     */
    @Override
    public Iterator<BufferedImage> iterator() {
        if (tileSize == 0) {
            throw new IllegalArgumentException(
                    "You need to set the tile size first.");
        }
        return new MosaiqueImageIterator(tileSize);
    }

    /**
     * Returns the BufferedImage that is used to build tiles on.
     * 
     * @return The image
     */
    public BufferedImage getBufferedImage() {
        return myBufferedImage;
    }

    /**
     * 
     * @author tk
     * 
     */
    private class MosaiqueImageIterator implements Iterator<BufferedImage> {
        /**
         * 
         */
        Integer c = 0;

        /**
         * 
         */
        Integer r = 0;

        /**
         * 
         */
        Integer myTileSize;

        /**
         * The number of tile rows
         */
        private int numRows;

        /**
         * The number of tile columns
         */
        private int numCols;

        /**
         * 
         * @param ts
         */
        public MosaiqueImageIterator(Integer ts) {
            myTileSize = ts;
            int rowPixel = myBufferedImage.getHeight();
            int colPixel = myBufferedImage.getWidth();

            numRows = rowPixel / myTileSize;
            numCols = colPixel / myTileSize;
        }

        /**
         * 
         * @return
         */
        public boolean hasNext() {
            return c < numCols && r < numRows;
        }

        /**
         * 
         * @return
         */
        public BufferedImage next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BufferedImage nextTile = myBufferedImage.getSubimage(
                    c * myTileSize, r * myTileSize, myTileSize, myTileSize);

            c++;
            if (c == numCols) {
                c = 0;
                r++;
            }
            return nextTile;
        }

        /**
         * 
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
