package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

 public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane pane = new BorderPane();
        pane.setMinSize(300, 400);
        pane.setTop(new TextField("field"));
        pane.setRight(new Button("press"));


        Group group = new Group();
        Scene scene = new Scene(group, 300, 400);
        group.getChildren().add(pane);


        primaryStage.setTitle("BasicApplication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawShapes(GraphicsContext gc) {
    }


    public static void main(String[] args) {
        launch(args);
    }
}
