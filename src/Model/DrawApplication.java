package Model;


import Model.Commands.CommandFactory;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;
import Model.Shapes.ShapeFactory;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Observer;

public class DrawApplication extends Application {

    public String[] getAvailableShapes(){
        return new ShapeFactory().getShapes();
    }

    public DrawApplication(){
    }


    @Override
    public void addCommand(String command, Map params) {
        super.addCommand(new CommandFactory().createCommand(command,params));
    }

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
