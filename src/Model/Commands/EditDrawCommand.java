package Model.Commands;

import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

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

    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        try {
            int dc = findFirstOccurance(x, y);
            Shape previousShape = ((DrawCommand) Command.getCommandHistory().get(dc)).getShape();
            Shape tempShape = ShapeFactory.createShape(shape, previousShape.getX(), previousShape.getY(), size, size, fill, color);
            ((DrawCommand) Command.getCommandHistory().get(dc)).setShape(tempShape);
            Command.getCommandHistory().addLast( new EditDrawCommand(previousShape,dc,tempShape));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }
}
