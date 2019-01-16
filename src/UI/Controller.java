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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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
    private ImageView rocketImage;

    private Timeline timeline;
    private final Timer timer = new Timer();
    private int index = 0;
    private double velScroll = 0;
    private double velScrollNext = 0;
    private double velScrollFinal = 0;
    private double blowJob = 0;
    private MotherClass mc = new MotherClass();
    private double mass = 0;
    private double labelForHeight = 0;
    private double labelForVel = 0;
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
                measurement.getSaveChartBttn().setDisable(false);
                measurement.getSaveChartBttn().setOnMouseClicked(e -> {
                    saveData();
                });
            });
            Stage stage2 = new Stage();
            stage2.setTitle("Landing Graph");
            stage2.setScene(new Scene(root2, 600, 501));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    void saveData() {
        if(series.getData().size() == 0){
            System.out.println("Not enough data");
        } else {
            System.out.println("Print data");
        }
    }

    @FXML
    void fuckingjava(ActionEvent event) {
        ArrayClass ac = new ArrayClass();
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
                    Platform.runLater(() -> {
                        series.getData().add(new XYChart.Data(labelForHeight,Math.abs(labelForVel)));
                    });
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
                            showWin();
                            startScroll.setDisable(false);
                        } else {
                            showLoose();
                            startScroll.setDisable(false);
                        }
                    }
                    if (velScrollFinal < 0) {
                        timeline.stop();
                        timer.cancel();
                        startScroll.setDisable(false);
                        showWenOuterspace();
                    }
                    setFire();
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

    public void showWin(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You win!");
        alert.setHeaderText("You survived the moon landing!");
        alert.setContentText("You have " + (mass-1000) +" fuel left and your final velocity was " + labelForVel + ".");

        alert.showAndWait();
    }

    public void showLoose(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over!");
        alert.setHeaderText("You didn't survive the moon landing!");
        alert.setContentText("Your final velocity was " + labelForVel + ".");

        alert.showAndWait();
    }

    public void showWenOuterspace(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Where did you go?");
        alert.setHeaderText("You were outer space!");
        alert.setContentText("Your final velocity was " + labelForVel + " and you had " + (mass-1000) + " fuel left.");

        alert.showAndWait();
    }

    public void setFire(){
        Platform.runLater(() -> {
            if(blowJob <= 0 && blowJob > -6){
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
                System.out.println("XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            } else if (blowJob <= -6){
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_1.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
                System.out.println("XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

            } else if (blowJob < -6 && blowJob > -10 ){
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_2.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
                System.out.println("XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

            } else {
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_3.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
                System.out.println("XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

            }
        });
    }
}

