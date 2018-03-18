package Model.Shapes;

import Model.Commands.Command;
import Model.Factory.AbstractFactory;

import java.util.Map;

/**
 * A factory that creates shapes based on shape name and input parameters
 */
public class ShapeFactory extends AbstractFactory{


    /**
     * @param name name of shape
     * @param params parameters
     * @return returns a created shape
     */
    public Shape createShape(String name, Map params){
        name = name.toUpperCase();
        try{
            switch(name){
                case "CIRCLE":
                    if(params.size() >= 6){
                        return new Circle(
                                (double)params.get("X"),
                                (double)params.get("Y"),
                                (int)params.get("WIDTH"),
                                (int)params.get("HEIGHT"),
                                (boolean)params.get("FILLED"),
                                (String)params.get("COLOR")
                        );
                    }
                case "SQUARE":
                    if(params.size() >= 6){
                        return new Square(
                                (double)params.get("X"),
                                (double)params.get("Y"),
                                (int)params.get("WIDTH"),
                                (int)params.get("HEIGHT"),
                                (boolean)params.get("FILLED"),
                                (String)params.get("COLOR")
                        );
                    }

                default:break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public Command createCommand(String name, Map params) {
        return null;
    }

    /**
     * @param name shape name
     * @param x x of shape
     * @param y y of shape
     * @param width width of shape
     * @param height height of shape
     * @param isFilled fill of shape
     * @param color color of shape
     * @return returns a constructed shape
     * creates a shape without a parameteres map just regular attributes
     */
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

    @Override
    public String[] getShapes(){
        String[] strs = {"Circle","Square"};
        return strs;
    }
}
