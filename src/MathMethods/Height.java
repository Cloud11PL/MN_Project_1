package MathMethods;

public class Height implements Overkill{

    @Override
    public double variable(double prevHeight, double vel) {
        double height = prevHeight - vel;
        return height;
    }
}
