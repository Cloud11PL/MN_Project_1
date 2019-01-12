package MathMethods;

public class Mass implements Overkill{

    @Override
    public double variable(double prevMass, double massChange) {
        double mass = prevMass + massChange;
        if(mass <= 1000){
            mass = prevMass;
        }
        return mass;
    }
}
