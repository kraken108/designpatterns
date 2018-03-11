package Model.Shapes;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum ShapeColor {
    BLACK (Color.BLACK.toString()),
    WHITE (Color.WHITE.toString()),
    RED(Color.RED.toString()),
    GREEN(Color.GREEN.toString()),
    BLUE(Color.BLUE.toString()),
    YELLOW(Color.YELLOW.toString());

    private final String color;

    ShapeColor(String c) {
        color = c;
    }

    public String toString() {
        return this.color;
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

}
