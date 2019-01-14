package MathMethods;

public class Velocity implements Overkill{

    @Override
    public double variable(double prevVel, double acc) {
        double velocity = prevVel + acc;
        System.out.println("vel:" + velocity);
        return velocity;

    }
}
