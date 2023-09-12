package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileHandlerXML {
    /**
     * Writes the given String to the given file.
     *
     * @param fileName the name of the file to write the string
     * @param str      the string to write on the file
     * @throws FileNotFoundException the exception to throw when the file is not found.
     */
    public static void writeToTextFile(String fileName, String str) throws FileNotFoundException {
        writeText(fileName, str, false);
    }

    /**
     * Appends the given String to the given file.
     *
     * @param fileName the name of the file to append to the String
     * @param str      the string to append the string
     * @throws FileNotFoundException the exception to throw when the file is not found.
     */
    public static void appendToTextFile(String fileName, String str) throws FileNotFoundException {
        writeText(fileName, str, true);
    }

    /**
     * Writes or append the text to a given file
     *
     * @param fileName the name of the file to write / append the string
     * @param str      the string to write / append
     * @param append   boolean, true to append , false to write
     * @throws FileNotFoundException
     */
    private static void writeText(String fileName, String str, boolean append)
            throws FileNotFoundException {
        PrintWriter writeToFile = null;

        try {
            FileOutputStream fileOutStream = new FileOutputStream(fileName, append);
            writeToFile = new PrintWriter(fileOutStream);
            writeToFile.println(str);
        } finally {
            if (writeToFile != null) {
                writeToFile.close();
            }
        }
    }
}


