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

    private DrawCommand drawCommand = new DrawCommand();
    private DeleteDrawCommand deleteDrawCommand = new DeleteDrawCommand();
    private EditDrawCommand editDrawCommand = new EditDrawCommand();

    /**
     * Get a Map representation of all shapes stored in the model.
     * @return
     */
    public List<Map> getShapes(){
        List<Map> shapes = new ArrayList<>();
        for(Command c : Command.getCommandHistory()){
            if(c instanceof DrawCommand){
                Shape s = ((DrawCommand) c).getShape();
                Map<String,Object> map = new HashMap<>();
                map.put("NAME",s.getClass().getSimpleName());
                map.put("X",s.getX());
                map.put("Y",s.getY());
                map.put("WIDTH",s.getWidth());
                map.put("HEIGHT",s.getHeight());
                map.put("FILLED",s.getIsFilled());
                map.put("COLOR",s.getColor());
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

    /**
     * Adds a new command to the application
     * @param command Name of command to be added.
     * @param params  Attributes the command should possess.
     */
    @Override
    public void addCommand(String command, Map params) {
        super.addCommand(FactoryProducer.getInstance().createFactory("COMMAND").createCommand(command,params));
    }

    /**
     * Edits a draw command.
     * @param x x value
     * @param y y value
     * @param shape the shape
     * @param size the size
     * @param fill is it filled
     * @param color color of shape
     */
    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        editDrawCommand.editDrawCommand(x,y,shape,size,fill,color);
    }

    /**
     * Removes a draw command at position
     * @param x x value
     * @param y y value
     */
    public void deleteDrawCommand(double x, double y){
        deleteDrawCommand.deleteDrawCommand(x,y);
    }

    /**
     * Constructor taking an observer, sends it to commands.
     * @param o The observer
     */
    public DrawApplication(Observer o) {
        super.commands.addObserver(o);
        drawCommand.addObserver(o);
        deleteDrawCommand.addObserver(o);
        editDrawCommand.addObserver(o);
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
        for(Command c : Command.getCommandHistory()){
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
