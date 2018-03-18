package Tests;

import Model.Application;
import Model.DrawApplication;

import java.util.*;

public class TestClass {

    public static void main(String[] args){

        //Application tests
        /*** ADD NEW SQUARE SHAPE ***/
        Application app = new DrawApplication();

        Map<String,Object> shapeMap = new HashMap<>();
        shapeMap.put("WIDTH",new Integer(50));
        shapeMap.put("HEIGHT", new Integer(50));
        shapeMap.put("X",new Double(100));
        shapeMap.put("Y",new Double(100));
        shapeMap.put("FILLED",new Boolean(true));
        shapeMap.put("COLOR",new String("FFFFF"));

        Map<String,Object> commandMap = new HashMap<>();
        commandMap.put("NAME","SQUARE");
        commandMap.put("ATTRIBUTES",shapeMap);

        app.addCommand("DRAW",commandMap);

        System.out.println("Current items:");
        System.out.println(app.toString());

        /** SAVE AND LOAD TESTS **/
        String filename = "myworld.txt";
        app.saveWorld(filename);
        app.clearApplication();
        System.out.println("Cleared app");
        System.out.println("Current items:");
        System.out.println(app.toString());

        app.openWorld(filename);
        System.out.println("Loaded world");
        System.out.println("Current items:");
        System.out.println(app.toString());
    }


}
