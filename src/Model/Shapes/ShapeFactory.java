package Model.Shapes;

public class ShapeFactory {

    public Shape createShape(String name, int x, int y, int width, int height, boolean isFilled) {
        name = name.toUpperCase();
        switch (name) {
            case "CIRCLE":
                return new Circle(x, y, width, height, isFilled);
            case "SQUARE":
                return new Square(x, y, width, height, isFilled);
            default:
                return null;
        }
    }

    public String[] getShapes(){
        String[] shapes = {"CIRCLE","SQUARE"};
        return shapes;
    }

}
