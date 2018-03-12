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

    final protected static int findFirstOccurance(double x,double y){
        Shape testShape;
        int i  = 0;
        for(Command c: Command.getCommandHistory()){
            if(c instanceof DrawCommand) {
                testShape = ((DrawCommand) c).getShape();
                if (x >= testShape.getX() && x <= testShape.getX() + testShape.getWidth()) {
                    if (y >= testShape.getY() && y <= testShape.getY() + testShape.getHeight()) {
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
            int dc = findFirstOccurance(x, y);
            System.out.println("first occ: "+dc);
            Shape previousShape = ((DrawCommand) Command.getCommandHistory().get(dc)).getShape();
            Command.getCommandHistory().remove(dc);
            Command.getCommandHistory().add(0, new DrawCommand(ShapeFactory.createShape(shape, previousShape.getX(), previousShape.getY(), size, size, fill, color)));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }

}
