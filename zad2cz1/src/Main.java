import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Date start = new Date(); /**/
        System.out.println("- - S T A R T - -");

        String file = "./res/set1";

        System.out.println("> making SOM");
        Layer som = new Layer(1000000, 0.9);
        som.loadInputs(file);
        som.initNeurons( som.inputs.size() );
        som.saveNeurons("./res/nens");

        System.out.print("> training");
        int one = Math.floorDiv(som.max_epochs, 100);
        for(int i=0, j=0; som.iterations < som.max_epochs; i++ ) {
            som.train( som.inputs.get( ThreadLocalRandom.current().nextInt(0, som.inputs.size()) ) );

            if( i%one == 0 ) {
                if( j < 5 && i > 0 ) { // purge weaklings   #temporary solution ?
                    for(int k=som.neurons.size()-1; k>=0; k--) {
                        if( som.neurons.get(k).response < j ) {
                            som.neurons.remove( som.neurons.get(k) );
                        }
                    }
                }

                System.out.print(".");
                som.saveNeurons("./res/gif/nens_" + j++);
            }
        }

        System.out.println("\n> done");
        som.saveNeurons("./res/nens1");

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
