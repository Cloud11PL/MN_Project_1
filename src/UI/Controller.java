package UI;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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

    @FXML
    private Pane sliderPain;

    @FXML
    private ProgressBar fuelBar;

    @FXML
    private Label labelVel;

    @FXML
    private Label labelH;

    @FXML
    private Button btn_reset;


    @FXML
    private Button showGraph;

    @FXML
    private Button startScroll;

    @FXML
    private ScatterChart<NumberAxis, NumberAxis> chart;

    @FXML
    private Button saveChartBttn;


    private double scrollAddHigh = 0.1;
    private double scrollAddLow = 0.001;
    private double scroll = scrollAddLow + 0;
    private Timeline timeline;
    private Timer timer = new Timer();
    private int index = 0;
    private double velScroll = 0;
    private double velScrollNext = 0;
    private double velScrollFinal = 0;
    private double blowJob = 0;
    private MotherClass mc = new MotherClass();
    private double mass = 0;
    private double labelForHeight = 0;
    private double labelForVel = 0;
    private ArrayClass ac = new ArrayClass();
    XYChart.Series series = new XYChart.Series();


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
    void resetGame(ActionEvent event) {
        timer = new Timer();
        ac.cleanArraysAreImportantForTheEnvironment();
        mc.setAcc(-1.63);
        mc.setH(50000);
        mc.setM(2730.14);
        mc.setV(-150);
        index =0;
        blowJob = 0;
        labelVel.setText("0");
        labelH.setText("0");
        startScroll.setDisable(false);
        if(!(series.getData().isEmpty())) {
            series.getData().clear();
        }

    }

    @FXML
    void initialize() {
        assert scrollImage != null : "fx:id=\"scrollImage\" was not injected: check your FXML file 'game.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'game.fxml'.";
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color:transparent;");
        labelVel.setText("0");
        labelH.setText("0");
        fuelBar.setProgress(1);
    }

    @FXML
    void showGraph(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("graph.fxml"));
            Parent root2 = loader.load();
            GraphController measurement = loader.getController();

            Platform.runLater(() -> {
                measurement.getChart().getData().add(series);
            });
            /*
            measurement.setCustomerSelectCallback(customer -> {
                Platform.runLater(() -> {
                    customer.getData().add(series);
                });
            });
            */

            
            measurement.setCustomerSelectCallbackBttn(customer -> {
                System.out.println(customer);
            });
            Stage stage2 = new Stage();
            stage2.setTitle("Landing Graph");
            stage2.setScene(new Scene(root2, 600, 501));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    /*
    KOMUNIKATY O WYGRANEJ I PRZEGRANEJ
     */

    @FXML
    void saveData(ActionEvent event) {
        System.out.println("XD");
    }

    @FXML
    void fuckingjava(ActionEvent event) {

        startScroll.setDisable(true);
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println(mass + " After burn mass");
                if (mass <= -blowJob + 1000.14) {
                    blowJob = 0;
                }
                mc.doImportantStuff(blowJob, ac);
                System.out.println(blowJob);
                mass = (double) ac.getMassList().get(index);
                if (index == 0) {
                    velScrollFinal = 0;
                    index++;
                } else {
                    labelForHeight = (double) ac.getHeightList().get(index);
                    labelForVel = (double) ac.getVelocityList().get(index);
                    velScroll = labelForHeight * -2e-5;
                    velScrollNext = (double) ac.getHeightList().get(index - 1) * -2e-5;
                    velScrollFinal += velScroll - velScrollNext;
                    index++;
                    Platform.runLater(() -> series.getData().add(new XYChart.Data(labelForHeight,Math.abs(labelForVel))));
                }
                Platform.runLater(() -> {
                    if (!((double) ac.getHeightList().get(index - 1) <= -(double) ac.getVelocityList().get(index - 1))) {
                        timeline = new Timeline(
                                new KeyFrame(Duration.seconds(0.1),
                                        new KeyValue(scrollPane.vvalueProperty(), velScrollFinal)));
                        System.out.println(velScrollFinal);
                        fuelBar.setProgress((mass - 1000) / 1730.14);
                        labelH.setText(String.valueOf(round(labelForHeight, 2)));
                        labelVel.setText(String.valueOf(round(labelForVel, 2)));
                        timeline.play();
                    } else {
                        timeline.stop();
                        timer.cancel();
                        if(-(double) ac.getVelocityList().get(index - 1) > 0 && -(double) ac.getVelocityList().get(index - 1) <=2) {
                            System.out.println("you did it you sick fuck");
                            startScroll.setDisable(false);
                        } else {
                            System.out.println("You fucked up boi");
                            startScroll.setDisable(false);
                        }
                    }
                    if (velScrollFinal < 0) {
                        timeline.stop();
                        timer.cancel();
                        System.out.println("Bitch where de fukk u think u goin");
                        startScroll.setDisable(false);
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(task, 100, 100);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

