package sample;

import MathMethods.ArrayClass;
import MathMethods.MotherClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private ScatterChart<Number, Number> chart1;

    @FXML
    private Pane lllllol;

    @FXML
    private Button butt;

    @FXML
    void topKek(ActionEvent event) {
        ArrayClass ac = new ArrayClass();
        MotherClass mc = new MotherClass();

        for(int i = 0; i< 100; i++){
            double a = 0;
            mc.doImportantStuff(a,ac);
        }
        for(int i = 0; i< 100; i++){
            double a = -14;
            mc.doImportantStuff(a,ac);
        }
        XYChart.Series<Number,Number> series = new XYChart.Series<>();

         for(int i = 0; i < ac.getHeightList().size();i++){
             series.getData().add(new XYChart.Data<>((double)ac.getVelocityList().get(i),(double)ac.getHeightList().get(i)));
         }
         chart1.getData().addAll(series);
    }

}
