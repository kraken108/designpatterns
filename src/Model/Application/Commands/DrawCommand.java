package Model.Commands;

import Model.Shapes.Shape;

public class DrawCommand extends Command {
    private Shape shape;

    public DrawCommand(Shape s){
        super();
        this.shape = s;
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

    public DrawCommand findFirstOccurance(double x,double y){
        Shape testShape;
        for(Command c: Command.getCommandHistory()){
            testShape = ((DrawCommand) c).getShape();
            if(x>=testShape.getX()+(testShape.getWidth()/2) && x<=((DrawCommand) c).getShape().getX()-(testShape.getWidth()/2)){
                if(y>=testShape.getY()+(testShape.getHeight()/2) && y<=((DrawCommand) c).getShape().getY()-(testShape.getHeight()/2)){
                    return (DrawCommand) c;
                }
            }
        }
        return null;
    }

    public void editDrawCommand(DrawCommand dc,Shape shape){
        for(Command c:Command.getCommandHistory()){
            if(((DrawCommand) c).equals(dc)){
                ((DrawCommand) c).setShape(shape);
            }
        }
    }

}
