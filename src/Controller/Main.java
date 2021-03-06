package Controller;


import Model.ApplicationI;
import Model.DrawApplication;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
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
    private ApplicationI application;
    private Button brush;
    private Button changer;
    private Button eraser;

    private DrawController drawController;

    private Stage primaryStage;

    /**
     * @param observable observable class
     * @param o updated object
     * Redraws canvas when commands gets updated
     */
    @Override
    public void update(Observable observable, Object o) {
        Canvas c = canvas;
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());

        List<Map> shapes = ((DrawApplication)application).getShapes();
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

    /**
     * Gets all references from the scenebuilder XML file so that they can be references in the future.
     */
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
                                            mi.setOnAction(event -> application.clearApplication());
                                        }else if(mi.getText().equals("Open…")){
                                            System.out.println("SETTING OPEN ACTION");
                                            mi.setOnAction(event -> {
                                                FileChooser fileChooser = new FileChooser();
                                                fileChooser.setTitle("Open file");
                                                File file = fileChooser.showOpenDialog(primaryStage);
                                                if(file != null){
                                                    application.openWorld(file.getAbsolutePath());
                                                }
                                            });
                                        }else if(mi.getText().equals("Save")){
                                            System.out.println("SETTING SAVE ACTION");
                                            mi.setOnAction(event -> {
                                                FileChooser fileChooser = new FileChooser();
                                                fileChooser.setTitle("Save file");
                                                File file = fileChooser.showSaveDialog(primaryStage);
                                                if(file != null){
                                                    application.saveWorld(file.getAbsolutePath());
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
                                                            ((DrawApplication)application).getAvailableShapes();
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

    /**
     * @param node borderpane that holds the node
     * Initializes the tools and sets the corerct listeners
     */
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

    /**
     * @param shapeVal chosen shape
     * @param colorVal chosen color
     * @param sizeVal chosen size
     * @param tool chosen tool
     * Updates the canvaslistener depending on the input values
     */
    private void updateShapeCanvasListener(int shapeVal, int colorVal, int sizeVal,int tool) {
        String shapeName = (String) shapeChoices.getItems().get(shapeVal);
        String color = (String) colorChoices.getItems().get(colorVal);
        int size = Integer.parseInt((String) sizeChoices.getItems().get(sizeVal));
        setTool(tool, shapeName, color, size);
    }

    /**
     * @param tool what tool is chosen
     * sets what how the canvas should react when being clicked
     */
    private void setCanvasListener(int tool){
        String shape = (String) shapeChoices.getSelectionModel().getSelectedItem();
        String color = (String) colorChoices.getSelectionModel().getSelectedItem();
        int size = Integer.parseInt((String) sizeChoices.getSelectionModel().getSelectedItem());
        System.out.println("shape: " + shape + " color: " + color + " size: " + size + " fillbox: " + fillBox.isSelected());
        setTool(tool, shape, color, size);
    }

    private double enterDragX;
    private double enterDragY;

    private void setTool(int tool, String shape, String color, int size) {
        switch (tool) {
            case BRUSH:
                canvas.setOnMouseReleased((MouseEvent event) ->
                        drawController.addDrawCommand(shape, event.getX(), event.getY(), size, size, fillBox.isSelected(), color,((DrawApplication)application)));
                canvas.setOnMouseDragged((MouseEvent event) ->
                        drawController.addDrawCommand(shape, event.getX(), event.getY(), size, size, fillBox.isSelected(), color,((DrawApplication)application)));
                break;
            case CHANGER:
                System.out.println("changer");
                canvas.setOnMouseReleased((MouseEvent e)-> drawController.addEditCommand(e.getX(),e.getY(),
                        shape,size,fillBox.isSelected(),color,((DrawApplication)application)));
                canvas.setOnMouseDragged((MouseEvent e)-> System.out.print(""));
                canvas.setOnMouseReleased((MouseEvent e)-> drawController.addEditCommand(e.getX(),e.getY(),
                        shape,size,fillBox.isSelected(),color,((DrawApplication)application)));
                canvas.setOnMouseDragReleased(e ->
                    drawController.addEditGroupCommand(enterDragX,enterDragY,e.getX(),e.getY(),shape,size,fillBox.isSelected(),color,(DrawApplication)application));
                break;
            case ERASER:
                canvas.setOnMouseDragged((MouseEvent e)-> System.out.print(""));
                canvas.setOnMouseReleased((MouseEvent e)-> drawController.addDeleteCommand(e.getX(),e.getY(),(DrawApplication)application));
                canvas.setOnMouseDragReleased(e -> drawController.addDeleteGroupCommand(enterDragX,enterDragY,e.getX(),e.getY(),(DrawApplication)application));
        }
        canvas.setOnMousePressed((MouseEvent e)->{
            enterDragX=e.getX();
            enterDragY=e.getY();
        });
        canvas.setOnDragDetected(event -> canvas.startFullDrag());
    }

    /**
     * @param node borderpane that holds the buttons
     * initialises undo and redo buttons and decides what t odo on aciton
     */
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

    /**
     * @param nd the canvas
     * sets up the canvas
     */
    private void initializeCanvas(Node nd){
        canvas = (Canvas) nd;
        canvas.setHeight(SCREEN_HEIGHT);
        canvas.setWidth(SCREEN_WIDTH);
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        setCanvasListener(BRUSH);
    }

    /**
     * @param tool chosen tool
     * sets the choiceboxlisteners
     */
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

    /**
     * @param node borderpane that holds choicebox
     * @param whichChoiceBox which choicebox is being initialized
     * @param target target choicesbox
     * @param strings strings to fill the coicesbox with
     * Fills the choiceboxes with information and chooses 0
     */
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

    /**
     * @param primaryStage stage
     * initilizes stage.
     */
    private void initializeStage(Stage primaryStage){
        primaryStage.setTitle("Paint - Ultimate Edition");
        primaryStage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
        primaryStage.show();
    }
}
