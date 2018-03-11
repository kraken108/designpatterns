package Model.Shapes;

import javafx.scene.paint.Color;

public class Square extends Shape{


    @Override
    protected Square clone() throws CloneNotSupportedException {
        return new Square(getX(),getY(),getWidth(),getHeight(),getIsFilled(),getColor());
    }

    public Square(double x, double y, int width, int height, boolean isFilled,String color){
        super(x,y,width,height,isFilled,color);
    }
}
