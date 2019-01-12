package MathMethods;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

public class MotherClass {

    Overkill mass = new Mass();
    Overkill height = new Height();
    Overkill velocity = new Velocity();
    Overkill accelerate = new Acceleration();


    private double v = -150;
    private double h = 50000;
    private double massFuel0 = 1730.14;
    private double fuelBurn0 = 0;
    private double m = massFuel0 + 1000;
    private double acc = accelerate.variable(m, fuelBurn0);
    private ArrayClass ac = new ArrayClass();
    private Timeline timeline = new Timeline();
    private KeyEvent event;
    private KeyFrame getNewVal = new KeyFrame(Duration.millis(1000), actionEvent -> fuelBurnInc());
    private KeyFrame countNewVal = new KeyFrame(Duration.millis(100), e -> doImportantStuff(fuelBurnInc(), ac));

    public void doImportantStuff(double fuelBurn, ArrayClass ac) {

        ac.update(h,m,v,acc);

        double mNew = mass.variable(m, fuelBurn);
        double vNew = velocity.variable(v, acc);
        double hNew = height.variable(h, v);
        double accNew = accelerate.variable(m, fuelBurn);

        m = mNew;
        v = vNew;
        h = hNew;
        acc = accNew;

    }

     public double fuelBurnInc(){
        if(fuelBurn0 <=0 &&fuelBurn0 >=- 16.5) {
            switch(event.getCode()) {
                case D: fuelBurn0 += -0.5;
                case A: fuelBurn0 -= -0.5;
            }
        }
        return fuelBurn0;
     }

     public void myTime(){

        timeline.getKeyFrames().addAll(getNewVal,countNewVal);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
     }

     public void breakingAnimation(){
        timeline.pause();
     }
//
//    do {
//    } while (h = 0) {
//        if (v =< 2){
//            System.out.println("Win");
//        } else {
//            System.out.println("Loose");
//        }
//    }
//


}
