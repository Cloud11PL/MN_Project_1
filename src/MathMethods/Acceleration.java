package MathMethods;

public class Acceleration implements Overkill {



    @Override
    public double variable(double currentMass, double massChange) {
        double gravity = 1.63;
        double k = 636;
        double acceleration = -gravity - k*massChange/currentMass;
        return acceleration;
    }
}
