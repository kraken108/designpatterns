package Controller;

import Model.DrawApplication;
import Model.Commands.Command;
import Model.Commands.DrawCommand;
import Model.Shapes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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

    private final static int BRUSH = 0;
    private final static int CHANGER = 1;
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

    public void draw(Shape s){
        gc.setStroke(Color.valueOf(s.getColor()));
        gc.setFill(Color.valueOf(s.getColor()));
        if(s instanceof Square){
            if(s.getIsFilled()){
                gc.fillRect(s.getX(),s.getY(),s.getHeight(),s.getWidth());
            }else
                gc.strokeRect(s.getX(),s.getY(),s.getHeight(),s.getWidth());
        }else if (s instanceof Circle){
            if(s.getIsFilled()){
                gc.fillRect(s.getX(),s.getY(),s.getHeight(),s.getWidth());
            }else
                gc.strokeOval(s.getX(),s.getY(),s.getHeight(),s.getWidth());
        }
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
        System.out.println("clr:" +Color.BLACK.toString());
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
                             if (nd.getId().equals("hbox")) {
                                hbox = (HBox) nd;
                                for(Node nod : (hbox.getChildren())){
                                    if(nod.getId().equals("shapeSection")){
                                        for(Node node:((BorderPane) nod).getChildren()) {
                                            try {
                                                if (node.getId().equals("fillBox")) {
                                                    fillBox = (CheckBox) node;
                                                } else if (node.getId().equals("shapes")) {
                                                    String[] strings = {"Square", "Circle"};
                                                    initializeChoices((BorderPane) nod, SHAPES, "shapes", strings);
                                                }
                                            } catch (NullPointerException e) {}
                                        }
                                    }else if(nod.getId().equals("colorSection")){
                                        initializeChoices((BorderPane) nod,COLORS,"colors", ShapeColor.getNames(ShapeColor.class));
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
                }
            }catch(NullPointerException e){
                System.out.println("hehe");
            }
        }
    }

    private void updateShapeCanvasListener(int shapeVal,int colorVal, int sizeVal){
        String shape = (String) shapeChoices.getItems().get(shapeVal);
        String color = (String) colorChoices.getItems().get(colorVal);
        int size = Integer.parseInt((String)sizeChoices.getItems().get(sizeVal));
        System.out.println("shape: "+shape+" color: "+color+" size: "+size+" fillbox: "+fillBox.isSelected());
        canvas.setOnMousePressed((MouseEvent event)-> application.addCommand(
                new DrawCommand(ShapeFactory.createShape(
                        shape,event.getX(),event.getY(),size,size,fillBox.isSelected(),color))));
        canvas.setOnMouseDragged((MouseEvent event)-> application.addCommand(
                new DrawCommand(ShapeFactory.createShape(
                        shape,event.getX(),event.getY(),size,size,fillBox.isSelected(),color))));
    }

    private void setCanvasListener(int tool){
        String shape = (String) shapeChoices.getSelectionModel().getSelectedItem();
        String color = (String) colorChoices.getSelectionModel().getSelectedItem();
        int size = Integer.parseInt((String)sizeChoices.getSelectionModel().getSelectedItem());
        System.out.println("shape: "+shape+" color: "+color+" size: "+size+" fillbox: "+fillBox.isSelected());
        switch(tool){
            case BRUSH:
                canvas.setOnMousePressed((MouseEvent event)-> application.addCommand(
                        new DrawCommand(ShapeFactory.createShape(
                                shape,event.getX(),event.getY(),size,size,fillBox.isSelected(),color))));
                canvas.setOnMouseDragged((MouseEvent event)-> application.addCommand(
                        new DrawCommand(ShapeFactory.createShape(
                                shape,event.getX(),event.getY(),size,size,fillBox.isSelected(),color))));

                break;
            case CHANGER:
                //canvas.setOnMousePressed((MouseEvent e)->)//TODO: gå baklänges genom command....
                canvas.setOnMouseDragged((MouseEvent event)-> System.out.print(""));
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
        System.out.println("gggggg");
        int shape = shapeChoices.getSelectionModel().getSelectedIndex();
        int color = colorChoices.getSelectionModel().getSelectedIndex();
        int size = sizeChoices.getSelectionModel().getSelectedIndex();
        shapeChoices.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) ->
                    updateShapeCanvasListener((int)number2,color,size)
                );

        colorChoices.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) ->
                    updateShapeCanvasListener(shape,(int)number2,size)
                );

        sizeChoices.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) ->
                    updateShapeCanvasListener(shape,color,(int)number2)
                 );
    }

    private void initializeChoices(BorderPane node, int whichChoiceBox, String target, Object[] strings){
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
