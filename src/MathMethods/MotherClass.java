package MathMethods;


public class MotherClass {

    Overkill mass = new Mass();
    Overkill height = new Height();
    Overkill velocity = new Velocity();
    Overkill accelerate = new Acceleration();


    private double v = -150;
    private double h = 50000;
    private double gravity = -1.63;
    private double massFuel0 = 1730.14;
    private double fuelBurn0 = 0;
    private double m = massFuel0 + 1000;
    private double acc = accelerate.variable(m, fuelBurn0);
//    private ArrayClass ac = new ArrayClass();


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

//
//        do {
//    } while (h = 0) {
//        if (v =< 2){
//            System.out.println("Win");
//        } else {
//            System.out.println("Loose");
//        }
//    }
//


}
