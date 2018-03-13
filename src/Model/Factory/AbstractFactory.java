package Model.Factory;

import Model.Commands.Command;
import Model.Shapes.Shape;

import java.util.Map;

/**
 *
 * AbstractFactory describes which methods a factory should implement.
 */
abstract public class AbstractFactory {
    /**
     * Creates a shape object with the classname name and attributes in param.
     * @param name The name of the Shape object.
     * @param params The attributes the Shape object should possess.
     * @return The created Shape object.
     */
    public abstract Shape createShape(String name, Map params);

    /**
     * Creates a command object with the classname name and attributes in param.
     * @param name The name of the Command object.
     * @param params The attributes the Command object should possess.
     * @return The created Comand object.
     */
    public abstract Command createCommand(String name, Map params);

    /**
     * Returns a list all available Shape types.
     * @return Available Shapes.
     */
    public abstract String[] getShapes();
}
