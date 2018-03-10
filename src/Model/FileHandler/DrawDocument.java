package Model.FileHandler;

import Model.*;
import Model.Application.Application;
import Model.Application.DrawApplication;
import Model.Shapes.Circle;
import Model.Shapes.Shape;
import Model.Shapes.Square;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DrawDocument extends Document {

    @Override
    void loadDocument(ArrayList<String> rows, Application world) {
        for(String s : rows){
            String[] strings = s.split(" ");
            if(strings.length >= 5){
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                int width = Integer.parseInt(strings[2]);
                int height = Integer.parseInt(strings[3]);
                boolean isFilled = Boolean.parseBoolean(strings[4]);
                String type = strings[5];
                type = type.toUpperCase();
                Shape shape = null;
                switch(type){
                    case "SQUARE":
                        shape = new Square(x,y,width,height,isFilled);
                        break;
                    case "CIRCLE":
                        shape = new Circle(x,y,width,height,isFilled);
                        break;
                    default: break;
                }
                if(shape != null){
                    world.addCommand(new DrawCommand(shape));
                }
            }else{
                System.out.println("Failed to read read row, less than 5 elements");
            }
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
                    sb.append(s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight() + " " + s.getIsFilled() + " " + s.getClass());
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
