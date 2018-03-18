package Model.Commands;

import Model.Application;
import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

/**
 * A class that edits a drawCommand
 */
public class EditDrawCommand extends Observable implements Command {
    private Shape prevShape;
    private Shape newShape;
    private int index;

    public Shape getNewShape() {
        return newShape;
    }

    public Shape getPrevShape() {
        return prevShape;
    }

    public int getIndex() {
        return index;
    }

    public EditDrawCommand(Shape c, int index,Shape s){
        prevShape = c;
        newShape = s;
        this.index = index;
    }

    public EditDrawCommand(){
        prevShape = null;
        newShape = null;
        index = 0;
    }

    /**
     * @param x Mouse clikc x
     * @param y Mouse clikc y
     * @param shape new shape
     * @param size new size
     * @param fill fill or no fill
     * @param color new color
     * Edits an existing drawcommand with the new attributes and saves the shpae incase you want tot revert.
     *
     */
    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        /*try {
            int dc = findFirstOccurance(x, y);
            Shape previousShape = ((DrawCommand) Command.getCommandHistory().get(dc)).getShape();
            Shape newShape = ShapeFactory.createShape(shape, previousShape.getX(), previousShape.getY(), size, size, fill, color);
            ((DrawCommand) getCommandHistory().get(dc)).setShape(newShape);
            Map<String,Object> params = new HashMap();
            params.put("OLDSHAPE",previousShape);
            params.put("INDEX",dc);
            params.put("NEWSHAPE",newShape);
            Command.getCommandHistory().addLast(FactoryProducer.getInstance().createFactory("COMMAND").createCommand("EDIT",params));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }*/
    }

    @Override
    public LinkedList<Command> undoCommand(LinkedList<Command> commands) {
        commands.set(this.getIndex(),new DrawCommand(this.getPrevShape()));
        return commands;
    }

    @Override
    public LinkedList<Command> redoCommand(LinkedList<Command> commands) {
        ((DrawCommand)commands.get(this.getIndex())).setShape(this.getNewShape());
        return commands;
    }

    @Override
    public LinkedList<Command> performCommand(LinkedList<Command> commands,Map<String, Object> params) {
        try {
            int dc = Application.findFirstOccurance((double)params.get("X"),(double) params.get("Y"));
            if(dc==-1){
                return commands;
            }
            Shape previousShape = ((DrawCommand) commands.get(dc)).getShape();
            Shape newShape = ShapeFactory.createShape((String)params.get("SHAPE"), previousShape.getX(), previousShape.getY(), (int) params.get("SIZE"),(int) params.get("SIZE"),(boolean) params.get("FILL"),(String) params.get("COLOR"));
            ((DrawCommand) commands.get(dc)).setShape(newShape);
            Map<String,Object> eParams = new HashMap();
            eParams.put("OLDSHAPE",previousShape);
            eParams.put("INDEX",dc);
            eParams.put("NEWSHAPE",newShape);
            commands.addLast(FactoryProducer.getInstance().createFactory("COMMAND").createCommand("EDIT",eParams));
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("no object found");
        }
        return commands;
    }
}
