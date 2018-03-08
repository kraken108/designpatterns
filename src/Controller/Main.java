package Controller;

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

public class Main extends Application {


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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        getJavaFxElementReferences();
        initializeStage(primaryStage);
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
                                                    initializeChoices((BorderPane) nod, shapeChoices, "shapes", strings);
                                                }
                                            } catch (NullPointerException e) {}
                                        }
                                    }else if(nod.getId().equals("colorSection")){
                                        String[] strings = {"White","Black","Red","Green","Blue","Yellow"};
                                        initializeChoices((BorderPane) nod,colorChoices,"colors",strings);
                                    }else if(nod.getId().equals("sizeSection")){
                                        String[] strings = {"1","2","4","8","16","32","64"};
                                        initializeChoices((BorderPane) nod,sizeChoices,"sizes",strings);
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
            gc.strokeRoundRect(event.getX(),event.getY(),2,2,5,5);
        });
        canvas.setOnMouseDragged((MouseEvent event)->{
            gc.strokeRoundRect(event.getX(),event.getY(),2,2,5,5);
            gc.stroke();
        });
    }

    private void initializeChoices(BorderPane node, ChoiceBox choiceBox, String target, String[] strings){
        System.out.println(target);
        for(Node n:node.getChildren()) {
            try {
                if (n.getId().equals(target)) {
                    choiceBox = (ChoiceBox) n;
                    choiceBox.getItems().addAll(strings);
                    choiceBox.getSelectionModel().select(0);
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
