import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Layer {
    List<Point> inputs;
    List<Neuron> neurons;
    private final double learning_mp_0;
    private double radius_0 = 0;
    final int max_epochs;
    protected int iterations = 0;
    protected double learning_mp;

    public Layer(int max_epochs, double learning_mp_0) {
        inputs = new ArrayList<Point>();
        neurons = new ArrayList<Neuron>();
        this.max_epochs = max_epochs;
        this.learning_mp = this.learning_mp_0 = learning_mp_0;
    }

    public int pickBMU(Point p) {
        int index = 0;
        double distanceOLD = euclideanDistance2D( neurons.get(0).weights, p );

        for(int i=0; i<neurons.size(); i++) {
            double distance = euclideanDistance2D( neurons.get(i).weights, p );
            neurons.get(i).distance = distance;

            if( distance < distanceOLD ) {
                distanceOLD = distance;
                index = i;
            }
        }

        return index;
    }

    public double pickBMPdistance4error(Point p) {
        double distanceOLD = euclideanDistance2D( inputs.get(0), p );

        for(int i=1; i<inputs.size(); i++) {
            double distance = euclideanDistance2D( inputs.get(i), p );

            if( distance < distanceOLD ) {
                distanceOLD = distance;
            }
        }

        return distanceOLD;
    }

    public double error() {
        double error = 0;
        for(int i=0; i<neurons.size(); i++) {
            error += pickBMPdistance4error( neurons.get(i).weights );
        }
        error /= (double)neurons.size();
        return error;
    }

    public double radius_t() {
        //double lambda = max_epochs / Math.log(radius_0);
        double lambda = (double)max_epochs;// / radius_0;
        return radius_0 * Math.exp(-(double)iterations / lambda);
    }

    public void train(Point p) {
        int index = pickBMU(p);

        for(int i=0; i<neurons.size(); i++) {
            double distance2bmu = euclideanDistance2D(neurons.get(i).weights, neurons.get(index).weights);

            if( distance2bmu < Math.pow(radius_t(), 2) ) {
                double theta = Math.exp( -Math.pow(distance2bmu, 2) / (2*Math.pow(radius_t(), 2)) ); // influence

                neurons.get(i).weights.x += theta * learning_mp * (p.x - neurons.get(i).weights.x);
                neurons.get(i).weights.y += theta * learning_mp * (p.y - neurons.get(i).weights.y);

                neurons.get(i).response++;
            }
        }

        learning_mp = learning_mp_0 * Math.exp(-(double)iterations / max_epochs); // beware hidden conversion!
        iterations++;
    } // #kohonen

    public void train_gas(Point p) {
        pickBMU(p);
        sortNeurons();

        for(int i=0; i<neurons.size() && i<10; i++) {
            double lambda = radius_0 * Math.pow(0.00001 / radius_0, (double) iterations / (double) max_epochs);
            double theta = Math.exp(-(double) i / lambda); // influence

            neurons.get(i).weights.x += theta * learning_mp * (p.x - neurons.get(i).weights.x);
            neurons.get(i).weights.y += theta * learning_mp * (p.y - neurons.get(i).weights.y);

            neurons.get(i).response++;
        }

        learning_mp = learning_mp_0 * Math.exp(-(double)iterations / max_epochs); // beware hidden conversion!
        //learning_mp = learning_mp_0 * Math.pow(0.001/radius_0, (double)iterations/(double)max_epochs);
        iterations++;
    } // #gas

    public void sortNeurons() {
        Collections.sort( neurons, new Sorter() );
    } // #gas

    public void loadInputs(String filepath) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filepath));
            String str;// = in.readLine();

            while( (str = in.readLine()) != null ) {
                String[] values = str.split("\t");
                inputs.add( new Point( Double.parseDouble(values[0]), Double.parseDouble(values[1]) ) );
            }

            in.close();
        }
        catch (IOException e) {
            System.exit(1);
        }
    }

    public void saveNeurons(String filepath) {
        try {
            BufferedWriter out = new BufferedWriter( new FileWriter( filepath ) );

            for(int i=0; i<neurons.size(); i++) {
                out.write( Double.toString(neurons.get(i).weights.x) + "\t" + Double.toString(neurons.get(i).weights.y) );

                if( i+1 < neurons.size() ) {
                    out.write("\n");
                }
            }

            out.close();
        }
        catch (IOException e) {
            System.exit(1);
        }
    }

    public void initNeurons(int quantity) {
        for(int i=0; i<quantity; i++) {
            neurons.add( new Neuron() );
        }

        for(int i=0; i<neurons.size(); i++) {
            double distance = Math.sqrt( Math.pow( (neurons.get(i).weights.x - 0), 2) + Math.pow( (neurons.get(i).weights.y - 0), 2) );

            if( distance > radius_0 ) {
                radius_0 = distance;
            }
        }
        radius_0 *= 0.5;
    }

    public double euclideanDistance2D(Point a, Point b) {
        return Math.sqrt( (a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y) );
    }

    public void draw() {
        Drawer draw = new Drawer(inputs, neurons);
    }
}
