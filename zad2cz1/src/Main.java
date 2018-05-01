import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        System.out.println("- - S T A R T - -");

        String file1 = "./res/set1";
        String file2 = "./res/set2";

        Layer som = new Layer();
        som.loadInputs(file1);
        som.initNeurons( som.inputs.size() );


        int asd = som.pickBMU(new Point(0, 0));
        System.out.println( asd );
        System.out.println( som.neurons.get(asd).info() );


        som.saveNeurons("./res/neurons");


        System.out.println("- - -S T O P- - -");
    }
}
