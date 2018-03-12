package Model.FileHandler;

import Model.Application;
import Model.Commands.Command;
import Model.Commands.DrawCommand;
import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawDocument extends Document {

    @Override
    void loadDocument(ArrayList<String> rows, Application world) {
        try{
            for(String s : rows) {
                String[] strings = s.split(" ");
                System.out.println("Before big eq 6");
                if (strings.length >= 6) {
                    System.out.println("After dewd");
                    double x = Double.parseDouble(strings[0]);
                    double y = Double.parseDouble(strings[1]);
                    int width = Integer.parseInt(strings[2]);
                    int height = Integer.parseInt(strings[3]);
                    boolean isFilled = Boolean.parseBoolean(strings[4]);
                    String type = strings[5];
                    String color = strings[6];

                    type = type.toUpperCase();

                    Map<String,Object> map = new HashMap<>();
                    map.put("X",x);
                    map.put("Y",y);
                    map.put("WIDTH",width);
                    map.put("HEIGHT",height);
                    map.put("FILLED",isFilled);
                    map.put("COLOR",color);

                    Shape shape = FactoryProducer.getInstance().createFactory("SHAPE").createShape(type,map);
                    System.out.println("shaperino");
                    if (shape != null) {
                        System.out.println("ADDING COMMAND from FILE");
                        world.addCommand(new DrawCommand(shape));
                    }
                } else {
                    System.out.println("Failed to read read row, less than 5 elements");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveDocument(String name,Application world){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(name);
            for(Command c : world.getCommands().getCommandHistory()){
                if(c instanceof DrawCommand){
                    Shape s = ((DrawCommand) c).getShape();
                    StringBuilder sb = new StringBuilder();
                    sb.append(s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight()
                            + " " + s.getIsFilled() + " " + s.getClass().getSimpleName() + " " + s.getColor());
                    writer.println(sb.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }
}
