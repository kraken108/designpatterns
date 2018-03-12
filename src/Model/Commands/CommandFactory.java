package Model.Commands;


import Model.Factory.AbstractFactory;
import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.util.List;
import java.util.Map;

public class CommandFactory extends AbstractFactory{


    @Override
    public Shape createShape(String name, Map params) {
        return null;
    }

    public Command createCommand(String command, Map params){
        command = command.toUpperCase();
        if(params.size() >= 2){
            try{
                switch(command){
                    case "DRAW":
                        Shape s = FactoryProducer.getInstance().createFactory("SHAPE").createShape((String)params.get("NAME"),(Map)params.get("ATTRIBUTES"));
                        return new DrawCommand(s);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String[] getShapes() {
        return new String[0];
    }

}
