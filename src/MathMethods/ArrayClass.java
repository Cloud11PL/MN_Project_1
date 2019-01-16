package MathMethods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class ArrayClass implements UpdateArrays {
    private ArrayList velocityList = new ArrayList();
    private ArrayList heightList = new ArrayList();
    private ArrayList accelerateList = new ArrayList();
    private ArrayList massList = new ArrayList();

    public ArrayList getVelocityList() {
        return velocityList;
    }

    public ArrayList getHeightList() {
        return heightList;
    }

    public ArrayList getAccelerateList() {
        return accelerateList;
    }

    public ArrayList getMassList() {
        return massList;
    }

    public void cleanArraysAreImportantForTheEnvironment() {
        velocityList.clear();
        heightList.clear();
        accelerateList.clear();
        massList.clear();
    }

    @Override
    public void update(double h, double m, double v, double acc) {
        velocityList.add(v);
        heightList.add(h);
        accelerateList.add(acc);
        massList.add(m);
    }

    public void saveToFile() {
        try {
            File velo = new File("C:\\Users\\uaxau\\IdeaProjects\\LontkaSexiBoi\\MN_Project_1\\src\\UI\\Velocity.txt");
            File heig = new File("C:\\Users\\uaxau\\IdeaProjects\\LontkaSexiBoi\\MN_Project_1\\src\\UI\\Height.txt");
            File mass = new File("C:\\Users\\uaxau\\IdeaProjects\\LontkaSexiBoi\\MN_Project_1\\src\\UI\\Mass.txt");
            File acce= new File("C:\\Users\\uaxau\\IdeaProjects\\LontkaSexiBoi\\MN_Project_1\\src\\UI\\Acceleration.txt");
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
        }
    }
}

