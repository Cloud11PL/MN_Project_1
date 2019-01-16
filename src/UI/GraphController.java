package UI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;

import java.util.function.Consumer;

public class GraphController {

    @FXML
    private ScatterChart<Number, Number> chart;

    @FXML
    private Button saveChartBttn;

//    @FXML
//    void saveData(ActionEvent event) {
//        System.out.println("LOLJESTEMNAINNYMFREDZIE");
//    }

    private String select = "XD";
    private Consumer<ScatterChart> customerSelectCallback ;
    private Consumer<Event> customerSelectCallbackBttn ;

    public void setCustomerSelectCallback(Consumer<ScatterChart> callback) {
        this.customerSelectCallback = callback ;
    }

    public void setCustomerSelectCallbackBttn(Consumer<Event> event){
        this.customerSelectCallbackBttn = event;
    }

    public String getSelect() {
        return select;
    }

    public void setChart(ScatterChart<Number, Number> chart) {
        this.chart = chart;
    }

    public Button getSaveChartBttn() {
        return saveChartBttn;
    }

    public ScatterChart<?, ?> getChart() {
        return chart;
    }

    @FXML
    void initialize() {
    }

}
