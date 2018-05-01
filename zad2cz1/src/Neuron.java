import java.util.concurrent.ThreadLocalRandom;

public class Neuron {
    Point  input;
    Point  weights;
    double output;

    public Neuron() {
        input = null;
        weights = new Point( ThreadLocalRandom.current().nextDouble(-1, 1), ThreadLocalRandom.current().nextDouble(-1, 1) );
    }

    public void setInput(Point p) {
        if( input == null ) {
            input = new Point(p);
        }
        else {
            this.input = p;
        }
    }

    public void setInput(double x, double y) {
        if( input == null ) {
            input = new Point(x, y);
        }
        else {
            this.input.x = x;
            this.input.y = y;
        }
    }

    public String info() {
        String str = new String();

        if( input != null ) {
            str = "input:     " + input.info() +
                "\nweights:   " + weights.info();
        }
        else {
            str = "weights:   " + weights.info();
        }

        return str;
    }
}
