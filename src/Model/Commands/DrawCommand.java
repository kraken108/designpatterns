package Model.Commands;


import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

public class DrawCommand extends Observable implements Command {
    private Shape shape;

    public DrawCommand(Shape s){
        super();
        this.shape = s;
    }

    public DrawCommand(){
        shape = null;
    }

    public Shape getShape(){
        return shape;
    }

    public void setShape(Shape newShape){
        shape = newShape;
    }

    @Override
    public void undoCommand() {

    }

    @Override
    public void redoCommand() {

    }

    @Override
    public void performCommand(LinkedList<Command> commands, Map<String, Object> params) {

    }

}
