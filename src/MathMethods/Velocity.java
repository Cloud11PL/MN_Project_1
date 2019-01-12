package MathMethods;

public class Velocity implements Overkill{

    @Override
    public double variable(double prevVel, double acc) {
        double velocity = prevVel + acc;
        return velocity;
    }
}
