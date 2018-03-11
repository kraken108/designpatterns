package Model.Shapes;

import java.util.Map;

public class ShapeFactory {


    public static Shape createShape(String name, Map params){
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

    public static Shape createShape(String name, double x, double y, int width, int height, boolean isFilled,String color) {
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

    public static String[] getShapes(){
        String[] strs = {"CIRCLE","SQUARE"};
        return strs;
    }
}
