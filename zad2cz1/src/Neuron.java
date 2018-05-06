import java.util.concurrent.ThreadLocalRandom;

public class Neuron {
    Point  weights;
    int response = 0;
    Double distance = (double)-1;

    public Neuron() {
        weights = new Point( ThreadLocalRandom.current().nextDouble(-1, 1),
                             ThreadLocalRandom.current().nextDouble(-1, 1) );
    }

    public String info() {
        return "weights:   " + weights.info() +
             "\nresponse:  " + response;
    }
}
