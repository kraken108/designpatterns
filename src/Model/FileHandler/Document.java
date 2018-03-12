package Model.FileHandler;

import Model.Application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/************************************/
/** TEMPLATE METHOD DESIGN PATTERN **/
/************************************/

abstract public class Document {

    public void openDocument(String name, Application world) throws FileNotFoundException {
        ArrayList<String> rows = readDocument(name); //Read from file
        loadDocument(rows,world);
    }

    abstract void loadDocument(ArrayList<String> rows,Application world);
    public abstract void saveDocument(String name,Application world) throws FileNotFoundException;


    public ArrayList<String> readDocument(String name) throws FileNotFoundException {
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
