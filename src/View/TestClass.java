package View;

import Model.*;

public class TestClass {

    public void main(String[] argsv){
        World w = new World();
        Shape s = new Square(100,100,100,100,true);
        Command c = new DrawCommand(s);
        w.addCommand(c);
    }
}
