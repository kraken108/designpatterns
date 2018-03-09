package Model.Shapes;

public class Circle extends Shape {

    @Override
    protected Circle clone() throws CloneNotSupportedException {
        return new Circle(getX(),getY(),getWidth(),getHeight(),getIsFilled());
    }

    public Circle(int x, int y, int width, int height, boolean isFilled){
        super(x,y,width,height,isFilled);
    }
}
