package MathMethods;

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

    public void setV(double v) {
        this.v = v;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setM(double m) {
        this.m = m;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public void doImportantStuff(double fuelBurn, ArrayClass ac) {
        ac.update(h,m,v,acc);
        double tStep = 0.01;
        double mNew = m+tStep*fuelBurn*10;
        double vNew = v + acc*tStep*10;
        double hNew = h + v/10;
        double gravity = 1.63;
        double k = 636;
        double acceleration = -gravity - k*fuelBurn/m;

        m = mNew;
        v = vNew;
        h =  hNew;
        acc = acc*tStep + acceleration;

    }
}
