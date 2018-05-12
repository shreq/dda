import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Neuron {
    Point  weights;
    Color color;
    int response = 0;
    Double distance = (double)-1;

    public Neuron() {
        weights = new Point( ThreadLocalRandom.current().nextDouble(-1, 1),
                             ThreadLocalRandom.current().nextDouble(-1, 1) );
        color = new Color( ThreadLocalRandom.current().nextInt(0, 255),
                           ThreadLocalRandom.current().nextInt(0, 255),
                           ThreadLocalRandom.current().nextInt(0, 255) );
    }
}
