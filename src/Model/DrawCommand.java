package Model;

public class DrawCommand extends Command {
    private Shape shape;

    public DrawCommand(Shape s){
        super();
        this.shape = s;
    }

    public Shape getShape(){
        return shape;
    }
}
