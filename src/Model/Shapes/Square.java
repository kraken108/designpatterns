package Model.Shapes;

public class Square extends Shape{


    @Override
    protected Square clone() throws CloneNotSupportedException {
        return new Square(getX(),getY(),getWidth(),getHeight(),getIsFilled());
    }

    public Square(double x, double y, int width, int height, boolean isFilled){
        super(x,y,width,height,isFilled);
    }
}
