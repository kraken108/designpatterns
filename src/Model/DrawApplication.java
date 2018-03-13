package Model;


import Model.Commands.*;
import Model.Factory.FactoryProducer;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;
import Model.Shapes.Shape;

import java.io.FileNotFoundException;
import java.util.*;

public class DrawApplication extends Application {

    public String[] getAvailableShapes(){
        return FactoryProducer.getInstance().createFactory("SHAPE").getShapes();
    }

    private DrawCommand drawCommand = new DrawCommand();
    private DeleteDrawCommand deleteDrawCommand = new DeleteDrawCommand();
    private EditDrawCommand editDrawCommand = new EditDrawCommand();

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
        super.addCommand(FactoryProducer.getInstance().createFactory("COMMAND").createCommand(command,params));
    }

    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        editDrawCommand.editDrawCommand(x,y,shape,size,fill,color);
    }

    public void deleteDrawCommand(double x, double y){
        deleteDrawCommand.deleteDrawCommand(x,y);
    }

    public DrawApplication(Observer o) {
        super.commands.addObserver(o);
        drawCommand.addObserver(o);
        deleteDrawCommand.addObserver(o);
        editDrawCommand.addObserver(o);
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
            System.out.println("Trying to open: " + fileName);
            s.openDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
