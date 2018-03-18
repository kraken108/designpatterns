package Model;


import Model.Commands.*;
import Model.Factory.FactoryProducer;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;
import Model.Shapes.Shape;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * DrawApplication is an implementation of Application. Describing application specific methods.
 */
public class DrawApplication extends Application {

    /**
     * Get available shapes from the model.
     * @return A list of all available shapes.
     */
    public String[] getAvailableShapes(){
        return FactoryProducer.getInstance().createFactory("SHAPE").getShapes();
    }

    //private DrawCommand drawCommand = new DrawCommand();
    //private DeleteDrawCommand deleteDrawCommand = new DeleteDrawCommand();
    //private EditDrawCommand editDrawCommand = new EditDrawCommand();


    /**
     * Get a Map representation of all shapes stored in the model.
     * @return
     */
    public List<Map> getShapes(){
        List<Map> shapes = new ArrayList<>();
            for (Command c :
                    commandHistory) {
                if (c instanceof DrawCommand) {
                    Shape s = ((DrawCommand) c).getShape();
                    Map<String, Object> map = new HashMap<>();
                    map.put("NAME", s.getClass().getSimpleName());
                    map.put("X", s.getX());
                    map.put("Y", s.getY());
                    map.put("WIDTH", s.getWidth());
                    map.put("HEIGHT", s.getHeight());
                    map.put("FILLED", s.getIsFilled());
                    map.put("COLOR", s.getColor());
                    shapes.add(map);
                }
            }
        return shapes;
    }

    /**
     * Empty constructor
     */
    public DrawApplication(){
    }

    public DrawApplication(Observer o) {
        super(o);
    }

    @Override
    public Command getCommands() {
        return null;
    }

    /**
     * Saves application to file.
     * @param fileName The filename to write application data to.
     */
    @Override
    public void saveWorld(String fileName){
        Document s = new DrawDocument();
        try {
            s.saveDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an application from file.
     * @param fileName The filename to read from.
     */
    @Override
    public void openWorld(String fileName){
        Document s = new DrawDocument();
        try {
            System.out.println("Trying to open: " + fileName);
            s.openDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Generates a String representation of draw application.
     * @return String represenation of draw application.
     */
    @Override
    public String toString(){
        String s = "";
        for(Command c : getCommandHistory()){
            if(c instanceof DrawCommand){
                s += ((DrawCommand) c).getShape().getWidth() + " "
                        + ((DrawCommand) c).getShape().getHeight() + " "
                        + ((DrawCommand) c).getShape().getX() + " "
                        + ((DrawCommand) c).getShape().getY() + " "
                        + ((DrawCommand) c).getShape().getIsFilled() + " "
                        + ((DrawCommand) c).getShape().getColor();
            }
        }
        return s;
    }
}
