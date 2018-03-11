package Model.Shapes;

import javafx.scene.paint.Color;

abstract public class Shape {
    private double x;
    private double y;
    private int width;
    private int height;
    private boolean isFilled;
    private String color;

    public Shape(double x, double y, int width, int height, boolean isFilled,String color){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setColor(color);
        this.setFilled(isFilled);
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIsFilled(boolean isFilled){
        this.isFilled = isFilled;
    }

    public boolean getIsFilled(){
        return isFilled;
    }

    public Shape(){
        this.setX(0);
        this.setY(0);
        this.setWidth(0);
        this.setHeight(0);
        this.setColor("#000000");
        this.isFilled = false;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
