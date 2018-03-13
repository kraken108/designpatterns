package Model.FileHandler;

import Model.Application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Document describes an algorithm for saving and opening documents.
 */
abstract public class Document {

    /**
     * First method to call when opening a new document.
     * @param name The filename of the document.
     * @param world A reference to the application calling this method.
     * @throws FileNotFoundException
     */
    final public void openDocument(String name, Application world) throws FileNotFoundException {
        ArrayList<String> rows = readDocument(name); //Read from file
        loadDocument(rows,world);
    }

    /**
     * Loads a document to the model.
     * @param rows A list of rows in the read document.
     * @param world A reference to the application the document should be loaded into.
     */
    abstract void loadDocument(ArrayList<String> rows,Application world);

    /**
     * Saves a model to a document.
     * @param name Filename of the saved document.
     * @param world The application where data should be saved from.
     * @throws FileNotFoundException
     */
    public abstract void saveDocument(String name,Application world) throws FileNotFoundException;


    /**
     * Reads all rows in a document and stores them in an ArrayList.
     * @param name Filename of the document.
     * @return An ArrayList of rows in the document.
     * @throws FileNotFoundException
     */
    final public ArrayList<String> readDocument(String name) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(name));
        ArrayList<String> rows = new ArrayList<>();
        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println("Line: " + line);
                rows.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rows;
        }
    }
}
