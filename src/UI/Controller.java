package UI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView scrollImage;

    int x = 5;
    Animation animation;


    @FXML
    void initialize() {
        assert scrollImage != null : "fx:id=\"scrollImage\" was not injected: check your FXML file 'game.fxml'.";

        final Timer timer = new java.util.Timer();
        //      timer.scheduleAtFixedRate(new TimerTask() {

//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    scrollImage.yProperty().setValue(x);
//                    x += 5;
//                });
//            }
//        }, 0, 200);
//        scrollImage.localToScene(0,0);

//        Timeline timeline = new Timeline(
//                Animation animation = new Timeline(
//                new KeyFrame(Duration.seconds(2), e->{
//
//                            new KeyValue(scrollImage.yProperty(), 100);
//                    animation.play();
//                    System.out.println("XD");
//                }
//        ));
//        timeline.play();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2),
                                new KeyValue(scrollImage.yProperty(), 100.0)));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

            }
        }, 2000, 2000);

        /*
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                slowScrollToBottom(scrollImage);
            }
        }, 1, 3000);
        */


        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> slowScrollToBottom(scrollImage), 0, 5, TimeUnit.SECONDS);

    }

    void slowScrollToBottom(ImageView scrollImage) {
            Animation animation = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(scrollImage.yProperty(), 100.0)));
            animation.play();
        System.out.println("XD");
    }

}

