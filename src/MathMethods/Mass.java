package MathMethods;

public class Mass implements Overkill{

    @Override
    public double variable(double prevMass, double massChange) {
        double mass = prevMass - massChange;
        return mass;
    }
}
