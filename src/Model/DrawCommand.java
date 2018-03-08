package Model;

public class DrawCommand extends Command { // prototype
    private Shape shape;

    public DrawCommand(Shape s){
        this.shape = s;
    }




    public Shape getShape(){
        return shape;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new DrawCommand(this.shape);
    }
}
