package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {


    private final static int SCREEN_HEIGHT = 768;
    private final static int SCREEN_WIDTH = 1024;

    private Canvas canvas;
    private BorderPane borderPane;
    private HBox hbox;

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        try{
            for(Node n : root.getChildrenUnmodifiable()) {
                try{
                    if (n.getId().equals("borderpane")) {
                        borderPane = (BorderPane) n;
                        for (Node nd : borderPane.getChildren()) {
                            if (nd.getId().equals("canvas")) {
                                canvas = (Canvas) nd;
                            } else if (nd.getId().equals("hbox")) {
                                hbox = (HBox) nd;
                            }
                        }
                    }
                }catch(Exception e){
                    System.out.println("Fack off");
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        canvas.setHeight(SCREEN_HEIGHT);
        canvas.setWidth(SCREEN_WIDTH);

        canvas.getGraphicsContext2D().fillRect(0,0,100,100);


        primaryStage.setTitle("Paint - Ultimate Edition");
        primaryStage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
