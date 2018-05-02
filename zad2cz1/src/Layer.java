import java.io.*;
import java.util.ArrayList;
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

        for(int i=1; i<neurons.size(); i++) {
            double distance = euclideanDistance2D( neurons.get(i).weights, p);

            if( distance < distanceOLD ) {
                distanceOLD = distance;
                index = i;
            }
        }

        return index;
    }

    public double radius_t() {
        //double lambda = max_epochs / Math.log(radius_0);
        double lambda = max_epochs;// / radius_0;
        double radius_t = radius_0 * Math.exp(-iterations / lambda); // beware hidden conversion!
        return radius_t;
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
        //learning_mp -= learning_mp_0 * 0.1;
        iterations++;
    }

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
            // bamboozled
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
            // bamboozled
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

    public String infoInputs() {
        String str = "";

        for(int i=0; i<inputs.size(); i++) {
            str += inputs.get(i).info() + "\n";
        }

        return str;
    }

    public String infoNeurons() {
        String str = "";

        for(int i=0; i<neurons.size(); i++) {
            str += "- - - Neuron " + i + " - - -\n" + neurons.get(i).info() + "\n";
        }

        return str;
    }
}
