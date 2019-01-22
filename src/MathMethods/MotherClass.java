package MathMethods;

/**
 * Mother Class acting as main source of values.
 * The differential equations are calculated using Euler's method
 */

public class MotherClass {


    /**
     * Starting parameters
     */
    private double v = -150;
    private double h = 50000;
    private double massFuel0 = 1730.14;
    private double m = massFuel0 + 1000;
    private double acc = -1.63;


    /**
     * Setter for velocity used in restarting the simulation
     * @param v
     */
    public void setV(double v) {
        this.v = v;
    }

    /**
     * Setter for height used in restarting the simulation
     * @param h
     */

    public void setH(double h) {
        this.h = h;
    }

    /**
     * Setter for mass used in restarting the simulation
     * @param m
     */

    public void setM(double m) {
        this.m = m;
    }

    /**
     * Setter for acceleration used in restarting the simulation
     * @param acc
     */

    public void setAcc(double acc) {
        this.acc = acc;
    }

    /**
     * The core method for running the simulation
     * Implements Euler's method as a way to get values from differential equations given
     * @param fuelBurn
     * @param ac
     */

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
