package MathMethods;

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

    @Override
    public void update(double h, double m, double v, double acc) {
        velocityList.add(v);
        heightList.add(h);
        accelerateList.add(acc);
        massList.add(m);
    }
}
