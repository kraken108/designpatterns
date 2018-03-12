package Model.Factory;

import Model.Commands.Command;
import Model.Shapes.Shape;

import java.util.Map;

abstract public class AbstractFactory {
    public abstract Shape createShape(String name, Map params);
    public abstract Command createCommand(String name, Map params);
    public abstract String[] getShapes();
}
