package Model.Commands;

import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that edits a drawCommand
 */
public class EditDrawCommand extends Command {
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
        try {
            int dc = findFirstOccurance(x, y);
            Shape previousShape = ((DrawCommand) Command.getCommandHistory().get(dc)).getShape();
            Shape newShape = ShapeFactory.createShape(shape, previousShape.getX(), previousShape.getY(), size, size, fill, color);
            ((DrawCommand) Command.getCommandHistory().get(dc)).setShape(newShape);
            Map<String,Object> params = new HashMap();
            params.put("OLDSHAPE",previousShape);
            params.put("INDEX",dc);
            params.put("NEWSHAPE",newShape);
            Command.getCommandHistory().addLast(FactoryProducer.getInstance().createFactory("COMMAND").createCommand("EDIT",params));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }
}
