package UI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
--module-path "C:\Users\Wojciech\Documents\javafx-sdk-11.0.1\lib" --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.graphics,javafx.swing,javafx.web --add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED
 */

import MathMethods.ArrayClass;
import MathMethods.MotherClass;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML
    private ScrollPane scrollPane;

    int x = 5;
    Animation animation;
    double scrollAddHigh = 0.1;
    double scrollAddLow = 0.001;
    double scroll = scrollAddLow + 0;
    Timeline timeline;
    final Timer timer = new Timer();
    int index = 0;
    double velScroll = 0;
    double velScrollNext = 0;
    double velScrollFinal = 0;
    double blowJob = 0;

    @FXML
    private Button btn5;

    @FXML
    private Button btn10;

    @FXML
    private Button btn15;

    @FXML
    private Button btn0;


    @FXML
    void changeTo0(ActionEvent event) {
        blowJob = 0;
    }

    @FXML
    void changeTo10(ActionEvent event) {
        blowJob = -10;
    }

    @FXML
    void changeTo15(ActionEvent event) {
        blowJob = -15;

    }

    @FXML
    void changeTo5(ActionEvent event) {
        blowJob = -5;

    }

    @FXML
    private Button wypierdol;


    @FXML
    void wyjeb(ActionEvent event) {
        scroll += scrollAddHigh;
    }

    @FXML
    void initialize() {
        assert scrollImage != null : "fx:id=\"scrollImage\" was not injected: check your FXML file 'game.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'game.fxml'.";
        /*
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        */
        scrollPane.setBorder(null);


    }
    /*
    -150 to min predkosc
    -600 to max predkosc

    Operujemy scrollem od 0-1
    czyli
    1/450 = 0.0022
    czyli jedna jednostka predkosci to 0.0022 scrolla

    Dlatego odejmuje tam wartości aktualne od poprzednich
     */

    @FXML
    void fuckingjava(ActionEvent event) {
        ArrayClass ac = new ArrayClass();
        MotherClass mc = new MotherClass();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("XD");
                mc.doImportantStuff(blowJob, ac);
                /*
                Potrzeba nowej i wcześniejszej bo skacze w chuj
                 */
                /*
                First run
                initial = 0.33
                final = 0

                Second
                initial = 0.34
                previous = 0.33
                final = 0.34-0.33
                 */
                if (index == 0) {
                    velScrollFinal = 0;
                    index++;
                } else {
                    velScroll = (double) ac.getVelocityList().get(index) * -0.0022;
                    System.out.println("VelScroll " + velScroll);
                    velScrollNext = (double) ac.getVelocityList().get(index - 1) * -0.0022;
                    System.out.println("VelScroll " + velScrollNext);
                    velScrollFinal += velScroll - velScrollNext;
                    index++;
                }
                //velScroll = (double) ac.getVelocityList().get(index) * -0.0022; //To będzie nasza wartość początkowa
                Platform.runLater(() -> {
                            timeline = new Timeline(
                                    new KeyFrame(Duration.seconds(1),
                                            new KeyValue(scrollPane.vvalueProperty(), velScrollFinal)));
                            //timeline.setCycleCount(Animation.INDEFINITE);
                            //scroll += scrollAddLow;
                            System.out.println(velScrollFinal);
                            timeline.play();
                        }
                );
            }
        };

        timer.scheduleAtFixedRate(task, 100, 100);

    }
}
