package Model.Commands;


import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

public class DrawCommand extends Command {
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
    protected Object clone() throws CloneNotSupportedException {
        return new DrawCommand(this.shape);
    }

}
