package Model.Shapes;

import javafx.scene.paint.Color;

public class ShapeFactory {
    public static Shape createShape(String name, double x, double y, int width, int height, boolean isFilled,String color) {
        switch (name) {
            case "Circle":
                return new Circle(x, y, width, height, isFilled,color);
            case "Square":
                return new Square(x, y, width, height, isFilled,color);
            default:
                return null;
        }
    }
}
