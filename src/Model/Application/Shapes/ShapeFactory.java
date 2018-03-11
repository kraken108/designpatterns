package Model.Application.Shapes;

import java.util.List;
import java.util.Map;

public class ShapeFactory {


    public Shape createShape(String name, Map params){
        name = name.toUpperCase();
        try{
            switch(name){
                case "CIRCLE":
                    if(params.size() >= 6){
                        return new Circle((double)params.get("X"),
                                (double)params.get("Y"),
                                (int)params.get("WIDTH"),
                                (int)params.get("HEIGHT"),
                                (boolean)params.get("FILLED"),
                                (String)params.get("COLOR"));
                    }
                    break;
                case "SQUARE":
                    if(params.size() >= 6){
                        return new Square((double)params.get("X"),
                                (double)params.get("Y"),
                                (int)params.get("WIDTH"),
                                (int)params.get("HEIGHT"),
                                (boolean)params.get("FILLED"),
                                (String)params.get("COLOR"));
                    }
                    break;

                default:break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Shape createShape(String name, List<Object> params){
        name = name.toUpperCase();
        try{
            switch(name){
                case "CIRCLE":
                    if(params.size() >= 6){
                        return new Circle((double)params.get(0),(double)params.get(1),
                                (int)params.get(2),(int)params.get(3),
                                (boolean)params.get(4),
                                (String)params.get(5));
                    }
                    break;
                case "SQUARE":
                    if(params.size() >= 6){
                        return new Square((double)params.get(0),(double)params.get(1),
                                (int)params.get(2),(int)params.get(3),
                                (boolean)params.get(4),
                                (String)params.get(5));
                    }
                    break;
                default:return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }


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
