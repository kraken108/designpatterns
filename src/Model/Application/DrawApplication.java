package Model.Application;

import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;

import java.io.FileNotFoundException;
import java.util.Observer;

public class DrawApplication extends Application {

    public DrawApplication(Observer o) {
        super.commands.addObserver(o);
    }

    @Override
    public void saveWorld(String fileName){
        Document s = new DrawDocument();
        try {
            s.saveDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openWorld(String fileName){
        Document s = new DrawDocument();
        try {
            s.openDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
