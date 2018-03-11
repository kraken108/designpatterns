package Model.Commands;


import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.util.List;
import java.util.Map;

public class CommandFactory {


    public Command createCommand(String command, Map params){
        command = command.toUpperCase();
        if(params.size() >= 2){
            try{
                switch(command){
                    case "DRAW":
                        Shape s = new ShapeFactory().createShape((String)params.get("NAME"),(Map)params.get("ATTRIBUTES"));
                        return new DrawCommand(s);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
