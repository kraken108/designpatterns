package Model.Application.Commands;

import Model.Application.Shapes.Shape;
import Model.Application.Shapes.ShapeFactory;

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
    public Command createCommand(String command, List<Object> params){
        command = command.toUpperCase();
        if(params.size() >= 2){
            switch(command){
                case "DRAW":
                    Shape s = new ShapeFactory().createShape((String)params.get(0),(List<Object>) params.get(1));
                    return new DrawCommand(s);
                default:
                    return null;
            }
        }else{
            return null;
        }
    }
}
