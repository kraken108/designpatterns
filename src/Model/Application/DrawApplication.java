package Model.Application;

import Model.Commands.DrawCommand;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.io.FileNotFoundException;
import java.util.Observer;

public class DrawApplication extends Application {

    public DrawApplication(Observer o) {
        super.commands.addObserver(o);
    }

    public void addDrawCommand(String shapeName, double x, double y, int width, int height, boolean isFilled,String color){
        Shape s = new ShapeFactory().createShape(shapeName,x,y,width,height,isFilled,color);
        super.addCommand(new DrawCommand(s));
    }

    @Override
    public void saveWorld(String fileName){
        Document s = new DrawDocument();
        try {
            s.saveDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openWorld(String fileName){
        Document s = new DrawDocument();
        try {
            s.openDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
