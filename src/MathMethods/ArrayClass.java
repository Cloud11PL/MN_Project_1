package MathMethods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A working class containing arrays collecting data and saving them to txt files
 */
public class ArrayClass implements UpdateArrays {
    private ArrayList velocityList = new ArrayList();
    private ArrayList heightList = new ArrayList();
    private ArrayList accelerateList = new ArrayList();
    private ArrayList massList = new ArrayList();

    /**
     * Getter for velocity list
     * @return velocityList
     */

    public ArrayList getVelocityList() {
        return velocityList;
    }

    /**
     * Getter for height list
     * @return heightList
     */

    public ArrayList getHeightList() {
        return heightList;
    }

    /**
     * Getter for acceleration list
     * @return accelerateList
     */

    public ArrayList getAccelerateList() {
        return accelerateList;
    }

    /**
     * Getter for mass list
     * @return massList
     */

    public ArrayList getMassList() {
        return massList;
    }

    /**
     * A method clearing the arrays used in restarting the simulation
     */

    public void cleanArraysAreImportantForTheEnvironment(){
        velocityList.clear();
        heightList.clear();
        accelerateList.clear();
        massList.clear();
    }

    /**
     * A method implemented from UpdateArrays interface, is invoked when the simulation is running and saves the calculated data
     * @param h Height
     * @param m Mass
     * @param v Velocity
     * @param acc Acceleration
     */

    @Override
    public void update(double h, double m, double v, double acc) {
        velocityList.add(v);
        heightList.add(h);
        accelerateList.add(acc);
        massList.add(m);
    }

    /**
     * A method for file-saving using BufferedWriter
     */

    public void saveToFile() {
        try {
            File velo = new File("Velocity.txt");
            File heig = new File("Height.txt");
            File mass = new File("Mass.txt");
            File acce= new File("Acceleration.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(velo));
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(heig));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(mass));
            BufferedWriter bw3 = new BufferedWriter(new FileWriter(acce));

            for (int i = 0; i < velocityList.size(); i++) {
                bw.write((double) velocityList.get(i) + "\n");
                bw1.write((double) heightList.get(i) + "\n");
                bw2.write((double) massList.get(i) + "\n");
                bw3.write((double) accelerateList.get(i) + "\n");
            }
            bw.close();
            bw1.close();
            bw2.close();
            bw3.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
}
