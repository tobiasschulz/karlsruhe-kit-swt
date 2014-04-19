package edu.kit.ipd.Mosaique;

import java.io.IOException;

/**
 * Utility class to isolate command line call from the rest of the functionality
 * 
 * @author tk
 * 
 */
public final class Main {
    private Main() {
    }

    /**
     * Main method to be called from command line
     * 
     * @param args
     *            command line arguments
     * @throws IOException If an 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws IOException,
            ClassNotFoundException {
        /* Reading command line arguments */
        Configuration config = new Configuration(args);

        /*
         * Were there errors while parsing command line arguments? If yes, stop
         * here.
         */
        if (!config.isErrorFree()) {
            System.out.println("Error in command line arguments..");
            System.exit(1);
        }

        Mosaique myClassifier = new Mosaique(config);
        myClassifier.process();
    }
}
