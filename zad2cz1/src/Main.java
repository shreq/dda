import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws IOException {
    //menu
        int pick = 0;
        System.out.print("Pick algorithm:" +
                       "\n[1] Kohonen" +
                       "\n[2] Gas      ");
        try {
            pick = System.in.read();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if( pick != '1' && pick != '2' ) {
            System.exit(1);
        }

    // start
        String file = "./res/set1";
        BufferedWriter out = new BufferedWriter( new FileWriter("./res/error.dat"));

        System.out.println("> making SOM");
        Layer som = new Layer(100000, 0.999);
        som.loadInputs(file);
        som.initNeurons( som.inputs.size() );
        som.saveNeurons("./res/nens");
        int one = Math.floorDiv(som.max_epochs, 100);

    // train
        if( pick == '2' ) {
        // #gas
            System.out.print("> training with gas algorithm");
            for (int i = 0, j = 0; som.iterations < som.max_epochs; i++) {
                som.train_gas(som.inputs.get(ThreadLocalRandom.current().nextInt(0, som.inputs.size())));

                if (i % one == 0) {
                    if (j < 5 && i > 0) { // purge
                        for (int k = som.neurons.size() - 1; k >= 0; k--) {
                            if (som.neurons.get(k).response < j) {
                                som.neurons.remove(som.neurons.get(k));
                            }
                        }
                    }

                    System.out.print(".");
                    out.write(Integer.toString(j) + "\t" + Double.toString(som.error()) + "\n");
                    som.saveNeurons("./res/gif/nens_" + j++);
                }
            }
        }
        else {
        // #kohonen
            System.out.print("> training with kohonen algorithm");
            for (int i = 0, j = 0; som.iterations < som.max_epochs; i++) {
                som.train(som.inputs.get(ThreadLocalRandom.current().nextInt(0, som.inputs.size())));

                if (i % one == 0) {
                    if (j < 5 && i > 0) { // purge
                        for (int k = som.neurons.size() - 1; k >= 0; k--) {
                            if (som.neurons.get(k).response < j) {
                                som.neurons.remove(som.neurons.get(k));
                            }
                        }
                    }

                    System.out.print(".");
                    out.write(Integer.toString(j) + "\t" + Double.toString(som.error()) + "\n");
                    som.saveNeurons("./res/gif/nens_" + j++);
                }
            }
        }

    // end of training
        System.out.println("\n> done");
        som.saveNeurons("./res/nens1");
        try {
            out.close();
        }
        catch (IOException e) {
            System.exit(1);
        }

    // time to stop
        System.out.println( som.neurons.size() + " neurons survived the training" );
        System.out.println( "final error:  " + som.error() );
        som.saveNeurons("./res/nens2");
    }
}
