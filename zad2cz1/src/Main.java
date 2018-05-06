import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Date start = new Date(); /**/

    //menu
        int pick = 0;
        System.out.println("Pick algorithm:" +
                         "\n[1] Kohonen" +
                         "\n[2] gas");
        try {
            pick = System.in.read();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if( pick != '1' && pick != '2' ) {
            System.exit(1);
        }

    // start stuff
        System.out.println("- - S T A R T - -");
        String file = "./res/set1";

        System.out.println("> making SOM");
        Layer som = new Layer(100000, 0.999);
        som.loadInputs(file);
        som.initNeurons( som.inputs.size() );
        som.saveNeurons("./res/nens");
        int one = Math.floorDiv(som.max_epochs, 100);

    // continue stuff
        if( pick == '2' ) {
        // #gas
            System.out.print("> training with gas algorithm");
            for (int i = 0, j = 0; som.iterations < som.max_epochs; i++) {
                som.train_gas(som.inputs.get(ThreadLocalRandom.current().nextInt(0, som.inputs.size())));

                if (i % one == 0) {
                    if (j < 5 && i > 0) {
                        for (int k = som.neurons.size() - 1; k >= 0; k--) {
                            if (som.neurons.get(k).response < j) {
                                som.neurons.remove(som.neurons.get(k));
                            }
                        }
                    }

                    System.out.print(".");
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
                    if (j < 5 && i > 0) { // purge weaklings   #temporary solution ?
                        for (int k = som.neurons.size() - 1; k >= 0; k--) {
                            if (som.neurons.get(k).response < j) {
                                som.neurons.remove(som.neurons.get(k));
                            }
                        }
                    }

                    System.out.print(".");
                    som.saveNeurons("./res/gif/nens_" + j++);
                }
            }
        }

    // end of training
        System.out.println("\n> done");
        som.saveNeurons("./res/nens1");

    // time to stop
        System.out.println("- - -S T O P- - -");
        Date stop = new Date(); /**/
        System.out.println( stop.getTime() - start.getTime() + " ms" ); /**/

    // old solution for dead neurons, left just to make sure no dead neurons were left
        int dead = 0;
        for(int i=som.neurons.size()-1; i>=0; i--) {
            if( som.neurons.get(i).response < 5 ) {
                som.neurons.remove( som.neurons.get(i) );
                dead++;
            }
        }
        System.out.println( dead + " dead neurons were left" );
        System.out.println( som.neurons.size() + " neurons survived the training" );
        som.saveNeurons("./res/nens2");
    }
}
