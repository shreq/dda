import java.util.Comparator;

public class Sorter implements Comparator<Neuron> {

    @Override
    public int compare(Neuron a, Neuron b) {
        return a.distance.compareTo( b.distance );
    }
}
