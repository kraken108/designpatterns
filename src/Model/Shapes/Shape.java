package Model.Shapes;

abstract public class Shape implements Cloneable{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isFilled;

    public Shape(int x, int y, int width, int height, boolean isFilled){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
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
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
