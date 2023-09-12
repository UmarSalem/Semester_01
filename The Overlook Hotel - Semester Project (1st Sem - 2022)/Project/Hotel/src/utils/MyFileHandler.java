package utils;

import java.io.*;

/**
 *
 */
public class MyFileHandler implements Serializable {

    /**
     * Writes the given object to a file with the given file name
     *
     * @param fileName the name of file to write the object
     * @param obj      the object to write to the file
     * @throws FileNotFoundException the exception if the file is not found
     * @throws IOException           the exception if Java is unable to read files for some reason
     */
    public static void writeToBinaryFile(String fileName, Object obj) throws FileNotFoundException, IOException {
        ObjectOutputStream writeToFile = null;

        try {
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            writeToFile = new ObjectOutputStream(fileOutStream);

            writeToFile.writeObject(obj);
        } finally {
            if (writeToFile != null) {
                try {
                    writeToFile.close();
                } catch (IOException e) {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }
    }

    /**
     * // Reads the first object from the file with the given file name and returns it.
     *
     * @param fileName the name of file to write the object
     * @return the first object from the file
     * @throws FileNotFoundException  the exception if the file is not found
     * @throws IOException            the exception if Java is unable to read files for some reason
     * @throws ClassNotFoundException the exception if the class the object is casted to is not found.
     */

    public static Object readFromBinaryFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        Object obj = null;
        ObjectInputStream readFromFile = null;
        try {
            FileInputStream fileInStream = new FileInputStream(fileName);
            readFromFile = new ObjectInputStream(fileInStream);
            try {
                obj = readFromFile.readObject();
            } catch (EOFException eof) {
                //Done reading
            }
        } finally {
            if (readFromFile != null) {
                try {
                    readFromFile.close();
                } catch (IOException e) {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }

        return obj;
    }
}