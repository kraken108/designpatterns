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

    /**
     * @param command Input command
     * @param params Input parameters
     * @return Returns a command object.
     * Creates commands based on input command and parameters.
     */
    public Command createCommand(String command, Map params){
        command = command.toUpperCase();
            try{
                switch(command){
                    case "DRAW":
                        if(params.size() >= 2){
                            Shape s = FactoryProducer.getInstance().createFactory("SHAPE").createShape((String)params.get("NAME"),(Map)params.get("ATTRIBUTES"));
                            return new DrawCommand(s);
                        }
                    case "EDIT": return new EditDrawCommand((Shape)params.get("OLDSHAPE"),(int)params.get("INDEX"),(Shape)params.get("NEWSHAPE"));
                    case "DELETE": return new DeleteDrawCommand((Command)params.get("REMOVEDCOMMAND"),(int)params.get("INDEX"));
                    case "PLACEHOLDER": return new PlaceHolderCommand();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public String[] getShapes() {
        return new String[0];
    }

}
