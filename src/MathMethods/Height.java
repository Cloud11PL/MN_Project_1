package MathMethods;

public class Height implements Overkill{

    @Override
    public double variable(double prevHeight, double vel) {
        double height = prevHeight + vel;
        System.out.println("Height" + height);
        return height;
    }
}
