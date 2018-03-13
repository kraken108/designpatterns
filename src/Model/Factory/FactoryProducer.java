package Model.Factory;

import Model.Commands.CommandFactory;
import Model.Shapes.ShapeFactory;

/**
 * FactoryProducer is used to create and return factory objects.
 * Since only one instance of FactoryProducer is needed it is implemented as a Singleton.
 */
public class FactoryProducer {

    private final static FactoryProducer instance = new FactoryProducer();

    /**
     * Get the Singleton instance of FactoryProducer.
     * @return The FactoryProducer instance.
     */
    public static FactoryProducer getInstance(){
        return instance;
    }

    /**
     * Creates a new factory with the classname of name.
     * @param name Name of the desired factory class.
     * @return A factory if the factory class with name exists, else null.
     */
    public AbstractFactory createFactory(String name){
        name = name.toUpperCase();
        switch(name){
            case "COMMAND": return new CommandFactory();
            case "SHAPE": return new ShapeFactory();
            default:break;
        }
        return null;
    }
}
