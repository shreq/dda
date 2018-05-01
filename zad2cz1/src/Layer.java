import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Layer {
    List<Point>  inputs;
    List<Neuron> neurons;
    int    epochs = 0;
    double learning_mp = 0.1;
    double radius = 1;

    public Layer() {
        inputs = new ArrayList<Point>();
        neurons = new ArrayList<Neuron>();
    }

    public int pickBMU(Point p) {
        int index = 0;
        double distanceOLD = Math.sqrt( Math.pow( (neurons.get(0).weights.x - p.x), 2) + Math.pow( (neurons.get(0).weights.y - p.y), 2) );

        for(int i=1; i<neurons.size(); i++) {
            double distance = Math.sqrt( Math.pow( (neurons.get(i).weights.x - p.x), 2) + Math.pow( (neurons.get(i).weights.y - p.y), 2) );

            if( distance < distanceOLD ) {
                distanceOLD = distance;
                index = i;
            }
        }

        return index;
    }

    public void updateNeurons(int index) {
        double tempx = neurons.get(index).weights.x;
        double tempy = neurons.get(index).weights.y;
        neurons.get(index).weights.x = tempx + learning_mp * (neurons.get(index).input.x - tempx);
        neurons.get(index).weights.y = tempy + learning_mp * (neurons.get(index).input.y - tempy);
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
            //DataOutputStream out = new DataOutputStream( new FileOutputStream(filepath) );
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
