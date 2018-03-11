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

    private static int findFirstOccurance(double x,double y){
        Shape testShape;
        int i = 0;
        for(Command c: Command.getCommandHistory()){
            if(c instanceof DrawCommand) {
                testShape = ((DrawCommand) c).getShape();
                if (x >= testShape.getX() - testShape.getWidth() && x <= testShape.getX() + testShape.getWidth()) {
                    if (y >= testShape.getY() - testShape.getHeight() && y <= testShape.getY() + testShape.getHeight()) {
                        return i;
                    }
                }
            }
            i++;
        }
        return -1;
    }

    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        try {
            Command.getCommandHistory().set(findFirstOccurance(x, y), new DrawCommand(ShapeFactory.createShape(shape, x, y, size, size, fill, color)));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }

    public void deleteDrawCommand(double x,double y){
        int dc = findFirstOccurance(x,y);
        Command.getCommandHistory().remove(dc);
        setChanged();
        notifyObservers(commandHistory);
    }

}
