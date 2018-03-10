package Controller;

import Model.Application.DrawApplication;
import Model.Command;
import Model.DrawCommand;
import Model.Shapes.Circle;
import Model.Shapes.Shape;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {

    private final static int SHAPES = 0;
    private final static int COLORS = 1;
    private final static int SIZES = 2;
    private final static int SCREEN_HEIGHT = 768;
    private final static int SCREEN_WIDTH = 1024;
    private Parent root;
    private Canvas canvas;
    private GraphicsContext gc;
    private BorderPane borderPane;
    private ChoiceBox shapeChoices;
    private CheckBox fillBox;
    private ChoiceBox colorChoices;
    private ChoiceBox sizeChoices;
    private HBox hbox;
    private DrawApplication application;


    public void draw(Shape s){
        gc.strokeOval(s.getX(),s.getY(),s.getHeight(),s.getWidth());
    }

    @Override
    public void update(Observable observable, Object o) {
        LinkedList<Command> commands = ((LinkedList<Command>) o);
        Canvas c = canvas;
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        for (int i = 0; i < commands.size(); i++) {
            System.out.println(commands.get(i).toString());
            if(commands.get(i) instanceof DrawCommand){
                draw(((DrawCommand) commands.get(i)).getShape());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        getJavaFxElementReferences();
        initializeStage(primaryStage);
        application = new DrawApplication(this);
    }

    private void getJavaFxElementReferences(){
        try {
            root = FXMLLoader.load(getClass().getResource("../View/main.fxml"));
        } catch (IOException e) {
            System.out.println("Couldn't retreive Main.FXML");
            System.exit(0);
        }
        try{
            for(Node n : root.getChildrenUnmodifiable()) {
                try{
                    if (n.getId().equals("borderpane")) {
                        borderPane = (BorderPane) n;
                        for (Node nd : borderPane.getChildren()) {
                            if (nd.getId().equals("canvas")) {
                                initializeCanvas(nd);
                            } else if (nd.getId().equals("hbox")) {
                                hbox = (HBox) nd;
                                for(Node nod : (hbox.getChildren())){
                                    if(nod.getId().equals("shapeSection")){
                                        for(Node node:((BorderPane) nod).getChildren()) {
                                            try {
                                                if (node.getId().equals("fillBox")) {
                                                    fillBox = (CheckBox) node;
                                                } else if (node.getId().equals("shapes")) {
                                                    String[] strings = {"Square", "Circle", "Triangle"};
                                                    initializeChoices((BorderPane) nod, SHAPES, "shapes", strings);
                                                }
                                            } catch (NullPointerException e) {}
                                        }
                                    }else if(nod.getId().equals("colorSection")){
                                        String[] strings = {"White","Black","Red","Green","Blue","Yellow"};
                                        initializeChoices((BorderPane) nod,COLORS,"colors",strings);
                                    }else if(nod.getId().equals("sizeSection")){
                                        String[] strings = {"1","2","4","8","16","32","64"};
                                        initializeChoices((BorderPane) nod,SIZES,"sizes",strings);
                                    }
                                }
                            }
                        }
                    }
                }catch(NullPointerException e){
                    System.out.println("Fack off");
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void initializeCanvas(Node nd){
        canvas = (Canvas) nd;
        canvas.setHeight(SCREEN_HEIGHT);
        canvas.setWidth(SCREEN_WIDTH);
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        canvas.setOnMousePressed((MouseEvent event)->{
            application.addCommand(new DrawCommand(new Circle(event.getX(),event.getY(),2,2,true)));
        });
        canvas.setOnMouseDragged((MouseEvent event)->{
            application.addCommand(new DrawCommand(new Circle(event.getX(),event.getY(),2,2,true)));
        });
    }

    private void initializeChoices(BorderPane node, int whichChoiceBox, String target, String[] strings){
        System.out.println(target);
        for(Node n:node.getChildren()) {
            try {
                if (n.getId().equals(target)) {
                    switch(whichChoiceBox){
                        case 0:
                            shapeChoices = (ChoiceBox) n;
                            shapeChoices.getItems().addAll(strings);
                            shapeChoices.getSelectionModel().select(0);
                            break;
                        case 1:
                            colorChoices = (ChoiceBox) n;
                            colorChoices.getItems().addAll(strings);
                            colorChoices.getSelectionModel().select(0);
                            break;
                        case 2:
                            sizeChoices = (ChoiceBox) n;
                            sizeChoices.getItems().addAll(strings);
                            sizeChoices.getSelectionModel().select(0);
                            break;                    }
                }
            }catch(NullPointerException e){

            }
        }
    }

    private void initializeStage(Stage primaryStage){
        primaryStage.setTitle("Paint - Ultimate Edition");
        primaryStage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
        primaryStage.show();
    }
}
