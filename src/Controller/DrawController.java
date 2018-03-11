package Controller;

import Model.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class DrawController {


    public void draw(Map map,GraphicsContext gc) {

        try {
            gc.setStroke(Color.valueOf((String)map.get("COLOR")));
            gc.setFill(Color.valueOf((String)map.get("COLOR")));

            String name = (String) map.get("NAME");
            name = name.toUpperCase();
            switch (name) {
                case "CIRCLE":
                    if ((boolean) map.get("FILLED")) {
                        gc.fillOval(
                                (double) map.get("X"),
                                (double) map.get("Y"),
                                (int) map.get("WIDTH"),
                                (int) map.get("HEIGHT")
                        );
                    } else {
                        gc.strokeOval(
                                (double) map.get("X"),
                                (double) map.get("Y"),
                                (int) map.get("WIDTH"),
                                (int) map.get("HEIGHT")
                        );
                    }
                    break;

                case "SQUARE":
                    if ((boolean) map.get("FILLED")) {
                        gc.fillRect(
                                (double) map.get("X"),
                                (double) map.get("Y"),
                                (int) map.get("WIDTH"),
                                (int) map.get("HEIGHT")
                        );
                    }else{
                        gc.strokeRect(
                                (double) map.get("X"),
                                (double) map.get("Y"),
                                (int) map.get("WIDTH"),
                                (int) map.get("HEIGHT")
                        );
                    }

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addDrawCommand(String shape, double x, double y, int width, int height, boolean isFilled, String color,
                                Application application) {
        Map<String, Object> shapeMap = new HashMap<>();
        shapeMap.put("WIDTH", width);
        shapeMap.put("HEIGHT", height);
        shapeMap.put("X", x);
        shapeMap.put("Y", y);
        shapeMap.put("FILLED", isFilled);
        shapeMap.put("COLOR", color);

        Map<String, Object> commandMap = new HashMap<>();
        commandMap.put("NAME", shape);
        commandMap.put("ATTRIBUTES", shapeMap);

        application.addCommand("DRAW", commandMap);
    }
}
