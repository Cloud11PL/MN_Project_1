package UI;

import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;

public class GraphController {

    @FXML
    private ScatterChart<Number, Number> chart;

    @FXML
    private Button saveChartBttn;

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
