package UI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/*
--module-path "C:\Users\Wojciech\Documents\javafx-sdk-11.0.1\lib" --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.graphics,javafx.swing,javafx.web --add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED
 */

import MathMethods.ArrayClass;
import MathMethods.MotherClass;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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

    private double scrollAddHigh = 0.1;
    private double scrollAddLow = 0.001;
    private double scroll = scrollAddLow + 0;
    private Timeline timeline;
    private final Timer timer = new Timer();
    private int index = 0;
    private double velScroll = 0;
    private double velScrollNext = 0;
    private double velScrollFinal = 0;
    private double blowJob = 0;
    private MotherClass mc = new MotherClass();
    private double mass = 0;


    @FXML
    private void keyPressed(KeyEvent keyEvent) {

            if (blowJob <= 0 && blowJob >= -16.5) {
                switch (keyEvent.getCode()) {
                    case D:
                        blowJob += -0.5;
                        break;
                    case A:
                        blowJob -= -0.5;
                        break;
                }
                if (blowJob > 0) {
                    blowJob = 0;
                }
                if (blowJob < -16.5) {
                    blowJob = -16.5;
                }
            }


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

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color:transparent;");


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

        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println(mass + " After burn mass");
                if (mass <= -blowJob+1000.14) {
                    blowJob = 0;
                }

                mc.doImportantStuff(blowJob, ac);
                System.out.println(blowJob);
                mass = (double) ac.getMassList().get(index);

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
                    velScroll = (double) ac.getHeightList().get(index) * -2e-5;
                    //velScroll = (double) ac.getVelocityList().get(index) * -0.0022;
                    //System.out.println("VelScroll " + velScroll);
                    velScrollNext = (double) ac.getHeightList().get(index - 1) * -2e-5;
                    //velScrollNext = (double) ac.getVelocityList().get(index - 1) * -0.0022;
                    //System.out.println("VelScroll " + velScrollNext);
                    velScrollFinal += velScroll - velScrollNext;
                    index++;
                }
                //velScroll = (double) ac.getVelocityList().get(index) * -0.0022; //To będzie nasza wartość początkowa
                Platform.runLater(() -> {
                    if (!((double) ac.getHeightList().get(index - 1) <= -(double) ac.getVelocityList().get(index - 1))) {
                        timeline = new Timeline(
                                new KeyFrame(Duration.seconds(0.1),
                                        new KeyValue(scrollPane.vvalueProperty(), velScrollFinal)));
                        System.out.println(velScrollFinal);
                        timeline.play();

                    } else {
                        timeline.stop();
                        timer.cancel();
                        if(-(double) ac.getVelocityList().get(index - 1) > 0 && -(double) ac.getVelocityList().get(index - 1) <=2) {
                            System.out.println("you did it you sick fuck");
                        } else {
                            System.out.println("You fucked up boi");
                        }
                    }
                    if(velScrollFinal < 0){
                        timeline.stop();
                        timer.cancel();
                        System.out.println("Bitch where de fukk u think u goin");
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(task, 100, 100);

    }
}

