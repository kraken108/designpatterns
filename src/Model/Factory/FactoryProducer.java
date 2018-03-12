package Model.Factory;

import Model.Commands.CommandFactory;
import Model.Shapes.ShapeFactory;

public class FactoryProducer {

    private final static FactoryProducer instance = new FactoryProducer();

    public static FactoryProducer getInstance(){
        return instance;
    }

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
