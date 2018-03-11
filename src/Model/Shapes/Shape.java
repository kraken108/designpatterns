package Model.Shapes;

abstract public class Shape {
    private double x;
    private double y;
    private int width;
    private int height;
    private boolean isFilled;
    private String rgbColorCode;

    public Shape(double x, double y, int width, int height, boolean isFilled,String rgbColorCode){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setRgbColorCode(rgbColorCode);
        this.isFilled = isFilled;
    }

    public String getRgbColorCode() {
        return rgbColorCode;
    }

    public void setRgbColorCode(String rgbColorCode) {
        this.rgbColorCode = rgbColorCode;
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
        this.setRgbColorCode("0");
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
