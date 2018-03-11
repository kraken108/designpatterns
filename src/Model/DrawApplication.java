package Model;


import Model.Commands.Command;
import Model.Commands.CommandFactory;
import Model.Commands.DrawCommand;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.*;

public class DrawApplication extends Application {

    public String[] getAvailableShapes(){
        return new ShapeFactory().getShapes();
    }
    private DrawCommand drawCommand = new DrawCommand();


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

    public DrawApplication(){
    }

    @Override
    public void addCommand(String command, Map params) {
        super.addCommand(new CommandFactory().createCommand(command,params));
    }

    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        drawCommand.editDrawCommand(x,y,shape,size,fill,color);
    }

    public void deleteDrawCommand(double x, double y){
        drawCommand.deleteDrawCommand(x,y);
    }

    public DrawApplication(Observer o) {
        super.commands.addObserver(o);
        drawCommand.addObserver(o);
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
