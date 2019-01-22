package UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
    private Timer timer = new Timer();
    private int index = 0;
    private double velScroll = 0;
    private double velScrollNext = 0;
    private double velScrollFinal = 0;
    private double fuelOutput = 0;
    private MotherClass mc = new MotherClass();
    private double mass = 0;
    private double labelForHeight = 0;
    private double labelForVel = 0;
    private ArrayClass ac = new ArrayClass();
    private TimerTask task;
    XYChart.Series series = new XYChart.Series();
    private boolean wasPaused = false;

    /**
     * Method checks what type of key was pressed and fires a method attached to this particular key.
     * A and D keys change fuelOutput's value. F1 and ESC pauses and restarts the simulation.
     * @param keyEvent
     */
    @FXML
    private void keyPressed(KeyEvent keyEvent) {

        if (fuelOutput <= 0 && fuelOutput >= -16.5) {
            switch (keyEvent.getCode()) {
                case F1:
                    if(wasPaused){
                        wasPaused = false;
                        timer = new Timer();
                        go();
                        timer.scheduleAtFixedRate(task, 100, 100);
                    }
                    break;
                case ESCAPE:
                    timeline.pause();
                    timer.cancel();
                    wasPaused = true;
                    break;
                case D:
                    fuelOutput += -0.5;
                    break;
                case A:
                    fuelOutput -= -0.5;
                    break;
            }
            if (fuelOutput > 0) {
                fuelOutput = 0;
            }
            if (fuelOutput < -16.5) {
                fuelOutput = -16.5;
            }
        }
    }

    /**
     * Method assigns original values to parameters such as Acceleration, Height, Mass and Velocity.
     * It also clears specific UI elements.
     * @param event
     */
    @FXML
    void resetGame(ActionEvent event) {
        timer = new Timer();
        ac.cleanArraysAreImportantForTheEnvironment();
        mc.setAcc(-1.63);
        mc.setH(50000);
        mc.setM(2730.14);
        mc.setV(-150);
        index =0;
        fuelOutput = 0;
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

    /**
     * When Graph button is pressed, showGraph opens a new window with a graph.
     * @param event
     */
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

    /**
     * Checks if there is enough data to save. If so, saveToFile method is called.
     */
    void saveData() {
        if(series.getData().size() == 0){
            notEnoughDataAlert();
        } else {
            ac.saveToFile();
            dataWasSaved();
        }
    }

    /**
     * Starts a timer.
     * @param event
     */
    @FXML
    void startTimer(ActionEvent event) {
        go();
    }

    /**
     * This method is a core UI changing method and also the method responsible for calling MotherClass methods.
     * It calls MotherClass methods and assigns the output to specific variables. Then, the variables are converted to values that change the position of the ScrollPane.
     * These values are used in the Timeline method.
     * The 'go' method has implemented win/loose/went outer space logic.
     */
    private void go(){
        startScroll.setDisable(true);
        task = new TimerTask() {
            public void run() {
                System.out.println(mass + " After burn mass");
                if (mass <= -fuelOutput + 1000.14) {
                    fuelOutput = 0;
                }
                mc.doImportantStuff(fuelOutput, ac);
                System.out.println(fuelOutput);
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
                        setFire();
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
                });
            }
        };

        timer.scheduleAtFixedRate(task, 100, 100);

    }

    /**
     * Rounds values to desired places after comma.
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * Creates a win alert with a text containing mass and velocity.
     */
    public void showWin(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You win!");
        alert.setHeaderText("You survived the moon landing!");
        alert.setContentText("You have " + round((mass-1000),2) +" fuel left and your final velocity was " + round(labelForVel,2) + ".");

        alert.showAndWait();
    }

    /**
     * Creates a loose alert with the velocity upon death.
     */
    public void showLoose(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over!");
        alert.setHeaderText("You didn't survive the moon landing!");
        alert.setContentText("Your final velocity was " + round(labelForVel,2) + ".");

        alert.showAndWait();
    }

    /**
     * Creates a went outer space alert.
     */
    public void showWenOuterspace(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Where did you go?");
        alert.setHeaderText("You were outer space!");
        alert.setContentText("Your final velocity was " + round(labelForVel,2) + " and you had " + round((mass-1000),2) + " fuel left.");

        alert.showAndWait();
    }

    /**
     * Creates a 'not enough data' alert.
     */
    public void notEnoughDataAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save unsuccessful");
        alert.setHeaderText("Save unsuccessful");
        alert.setContentText("There's no data to save.");
        alert.showAndWait();
    }

    /**
     * Creates a 'not enough data' alert.
     */
    public void dataWasSaved(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save successful");
        alert.setHeaderText("Save successful");
        alert.setContentText("Data was saved to the main app folder.");
        alert.showAndWait();
    }

    /**
     * Method manipulating the sprite of the rocket according to the current fuelOutput.
     */
    public void setFire(){
        Platform.runLater(() -> {
            if(fuelOutput == 0){
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_none.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
            } else if(fuelOutput < 0 && fuelOutput >= -6){
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_1.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
            } else if (fuelOutput < -6 && fuelOutput > -10 ){
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_2.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
            } else {
                rocketImage.setImage(new Image(getClass().getResourceAsStream("assets/rocket_3.png")));
                rocketImage.setCache(true);
                rocketImage.setFitHeight(220);
                rocketImage.setFitWidth(137);
            }
        });
    }
}

