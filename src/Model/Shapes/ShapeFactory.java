package Model.Shapes;

public class ShapeFactory {

    public Shape createShape(String name, double x, double y, int width, int height, boolean isFilled,String color) {
        name = name.toUpperCase();
        switch (name) {
            case "CIRCLE":
                return new Circle(x, y, width, height, isFilled,color);
            case "SQUARE":
                return new Square(x, y, width, height, isFilled,color);
            default:
                return null;
        }
    }

    public String[] getShapes(){
        String[] shapes = {"CIRCLE","SQUARE"};
        return shapes;
    }

}
