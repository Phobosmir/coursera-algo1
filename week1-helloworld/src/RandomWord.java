import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        String word;
        int wordIndex = 0;

        while (!StdIn.isEmpty()) {
            word = StdIn.readString();
            wordIndex++;
            if (StdRandom.bernoulli(1.0/wordIndex))
                champion = word;

        }
        if (champion != null)
            StdOut.println(champion);

    }
}