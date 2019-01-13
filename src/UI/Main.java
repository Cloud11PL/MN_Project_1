package UI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root,500,800);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getRoot().requestFocus();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
