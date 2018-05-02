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
        Layer som = new Layer(10000, 0.9);
        som.loadInputs(file);
        som.initNeurons( som.inputs.size() );
        som.saveNeurons("./res/nens");

        System.out.print("> training");
        int ten = Math.floorDiv(som.max_epochs, 10);
        for(int i=0; som.iterations < som.max_epochs; i++ ) {
            som.train( som.inputs.get( ThreadLocalRandom.current().nextInt(0, som.inputs.size()) ) );

            if( i%ten == 0 ) System.out.print(".");
        }

        System.out.println("\n> done");
        som.saveNeurons("./res/nens1");

        System.out.println("- - -S T O P- - -");
        Date stop = new Date(); /**/
        System.out.println( stop.getTime() - start.getTime() + " ms" ); /**/


        // temporary solution for dead neurons
        int dead = 0;
        for(int i=som.neurons.size()-1; i>=0; i--) {
            if( som.neurons.get(i).response < 5 ) {
                som.neurons.remove( som.neurons.get(i) );
                dead++;
            }
        }
        System.out.println( dead );
        System.out.println( som.neurons.size() );
        som.saveNeurons("./res/nens2");
    }
}
