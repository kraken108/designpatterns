package Controller;


import Model.DrawApplication;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends Application implements Observer {

    private final static int BRUSH = 0;
    private final static int CHANGER = 1;
    private final static int ERASER = 2;
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
    private Button undo;
    private Button redo;
    private DrawApplication application;
    private Button brush;
    private Button changer;
    private Button eraser;

    private DrawController drawController;

    private Stage primaryStage;
    @Override
    public void update(Observable observable, Object o) {
        Canvas c = canvas;
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());

        List<Map> shapes = application.getShapes();
        for(Map map : shapes){
            Map<String,Object> m = map;
            drawController.draw(m,gc);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        application = new DrawApplication(this);
        getJavaFxElementReferences();
        initializeStage(primaryStage);
        drawController = new DrawController();
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
                    if(n instanceof MenuBar){
                        MenuBar menuBar = (MenuBar)n;
                        for(Menu m : menuBar.getMenus()){
                            if(m.getText().equals("File")){
                                for(MenuItem mi : m.getItems()){
                                    System.out.println("MENU ITEM:" + mi.getText());
                                    if(mi.getText() != null){
                                        if(mi.getText().equals("New")){
                                            System.out.println("SETTING NEW ACTION");
                                            mi.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    application.clearApplication();
                                                }
                                            });
                                        }else if(mi.getText().equals("Open…")){
                                            System.out.println("SETTING OPEN ACTION");
                                            mi.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    FileChooser fileChooser = new FileChooser();
                                                    fileChooser.setTitle("Open file");
                                                    File file = fileChooser.showOpenDialog(primaryStage);
                                                    if(file != null){
                                                        application.openWorld(file.getAbsolutePath());
                                                    }
                                                }
                                            });
                                        }else if(mi.getText().equals("Save")){
                                            System.out.println("SETTING SAVE ACTION");
                                            mi.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    FileChooser fileChooser = new FileChooser();
                                                    fileChooser.setTitle("Save file");
                                                    File file = fileChooser.showSaveDialog(primaryStage);
                                                    if(file != null){
                                                        application.saveWorld(file.getAbsolutePath());
                                                    }
                                                }
                                            });
                                        }
                                    }

                                }
                            }
                        }

                    }else if (n.getId().equals("borderpane")) {
                        borderPane = (BorderPane) n;
                        for (Node nd : borderPane.getChildren()) {
                             if (nd.getId().equals("hbox")) {
                                hbox = (HBox) nd;
                                for(Node nod : (hbox.getChildren())){
                                    if(nod.getId().equals("shapeSection")){
                                        for(Node node:((BorderPane) nod).getChildren()) {
                                            try {
                                                if (node.getId().equals("fillBox")) {
                                                    fillBox = (CheckBox) node;
                                                } else if (node.getId().equals("shapes")) {
                                                    String[] strings =
                                                            application.getAvailableShapes();
                                                    System.out.println("LENGTH OF STRINGS: " + strings.length);
                                                    //String[] strings = {"Square", "Circle"};

                                                    //get shapes from application
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
                                    }else if(nod.getId().equals("buttonSection")){
                                        initializeUndoRedoButtons((BorderPane) nod);
                                    }else if(nod.getId().equals("toolSection")){
                                        initializeTools((BorderPane) nod);
                                    }
                                }
                            }else if (nd.getId().equals("canvas")) {
                                initializeCanvas(nd);
                            }
                        }
                    }
                }catch(NullPointerException e){
                    System.out.println("Fack off");
                    e.printStackTrace();
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        setChoiceBoxListeners(BRUSH);
    }

    private void initializeTools(BorderPane node){
        System.out.println("tools");
        for(Node n:node.getChildren()){
            try{
                if(n.getId().equals("brush")){
                    brush = (Button) n;
                    brush.setOnAction(e-> {
                        setCanvasListener(BRUSH);
                        setChoiceBoxListeners(BRUSH);
                    });
                }else if(n.getId().equals("changer")){
                    changer = (Button) n;
                    changer.setOnAction(e->{
                        setCanvasListener(CHANGER);
                        setChoiceBoxListeners(CHANGER);
                    });
                }else if(n.getId().equals("eraser")){
                    eraser = (Button) n;
                    eraser.setOnAction(e->{
                        setCanvasListener(ERASER);
                        setChoiceBoxListeners(ERASER);
                    });
                }
            }catch(NullPointerException e){
                System.out.println("hehe");
            }
        }
    }

    private void updateShapeCanvasListener(int shapeVal, int colorVal, int sizeVal,int tool) {
        String shapeName = (String) shapeChoices.getItems().get(shapeVal);
        String color = (String) colorChoices.getItems().get(colorVal);
        int size = Integer.parseInt((String) sizeChoices.getItems().get(sizeVal));
        switch (tool) {
            case BRUSH:
                canvas.setOnMousePressed((MouseEvent event) ->
                        drawController.addDrawCommand(shapeName, event.getX(), event.getY(), size, size, fillBox.isSelected(), color,application));
                canvas.setOnMouseDragged((MouseEvent event) ->
                        drawController.addDrawCommand(shapeName, event.getX(), event.getY(), size, size, fillBox.isSelected(), color,application));
                break;
            case CHANGER:
                System.out.println("changer");
                canvas.setOnMousePressed((MouseEvent e)-> application.editDrawCommand(e.getX(),e.getY(),
                        shapeName,size,fillBox.isSelected(),color));
                canvas.setOnMouseDragged((MouseEvent e)-> System.out.println(""));
                break;
            case ERASER:
                canvas.setOnMousePressed((MouseEvent e)-> application.deleteDrawCommand(e.getX(),e.getY()));
                canvas.setOnMouseDragged((MouseEvent e)-> application.deleteDrawCommand(e.getX(),e.getY()));
                break;
        }
    }

    private void setCanvasListener(int tool){
        String shape = (String) shapeChoices.getSelectionModel().getSelectedItem();
        String color = (String) colorChoices.getSelectionModel().getSelectedItem();
        int size = Integer.parseInt((String) sizeChoices.getSelectionModel().getSelectedItem());
        System.out.println("shape: " + shape + " color: " + color + " size: " + size + " fillbox: " + fillBox.isSelected());
        switch (tool) {
            case BRUSH:
                canvas.setOnMousePressed((MouseEvent event) ->
                        drawController.addDrawCommand(shape, event.getX(), event.getY(), size, size, fillBox.isSelected(), color,application));
                canvas.setOnMouseDragged((MouseEvent event) ->
                        drawController.addDrawCommand(shape, event.getX(), event.getY(), size, size, fillBox.isSelected(), color,application));
                break;
            case CHANGER:
                canvas.setOnMousePressed((MouseEvent e)-> application.editDrawCommand(e.getX(),e.getY(),
                        shape,size,fillBox.isSelected(),color));
                canvas.setOnMouseDragged((MouseEvent e)-> System.out.println(""));
                break;
            case ERASER:
                canvas.setOnMousePressed((MouseEvent e)-> application.deleteDrawCommand(e.getX(),e.getY()));
                canvas.setOnMouseDragged((MouseEvent e)-> application.deleteDrawCommand(e.getX(),e.getY()));
                break;
        }
    }

    private void initializeUndoRedoButtons(BorderPane node){
        for(Node n:node.getChildren()){
            try{
                if(n.getId().equals("undo")){
                    undo = (Button) n;
                    undo.setOnAction(e-> application.undoCommand());
                }else if(n.getId().equals("redo")){
                    redo = (Button) n;
                    redo.setOnAction(e-> application.redoCommand());
                }
            }catch(NullPointerException e){
                System.out.println("hehe");
            }
        }
    }

    private void initializeCanvas(Node nd){
        canvas = (Canvas) nd;
        canvas.setHeight(SCREEN_HEIGHT);
        canvas.setWidth(SCREEN_WIDTH);
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        setCanvasListener(BRUSH);
    }

    private void setChoiceBoxListeners(int tool){
            shapeChoices.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) ->
                    updateShapeCanvasListener((int) number2, colorChoices.getSelectionModel().getSelectedIndex(),
                            sizeChoices.getSelectionModel().getSelectedIndex(),tool)
            );

            colorChoices.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) ->
                    updateShapeCanvasListener(shapeChoices.getSelectionModel().getSelectedIndex(),
                            (int) number2, sizeChoices.getSelectionModel().getSelectedIndex(),tool)
            );

            sizeChoices.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) ->
                    updateShapeCanvasListener(shapeChoices.getSelectionModel().getSelectedIndex(),
                            colorChoices.getSelectionModel().getSelectedIndex(), (int) number2,tool)
            );
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
