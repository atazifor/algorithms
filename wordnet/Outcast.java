/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description: Given a list of WordNet nouns x1, x2, ..., xn,
 *              which noun is the least related to the others?
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    /**
     * compute distance from each noun to every other noun,
     * and return the noun with the max distance from every other noun
     *
     * @param nouns
     * @return noun with max distance
     */
    public String outcast(String[] nouns) {
        String outcast = nouns[0];
        int max = -1;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordNet.distance(nouns[i], nouns[j]);
                /*StdOut.printf("[%s, %s] | sap = %s | distance = %d\n", nouns[i], nouns[j],
                              wordNet.sap(nouns[i], nouns[j]),
                              wordNet.distance(nouns[i], nouns[j]));*/
            }
            if (distance > max) {
                outcast = nouns[i];
                max = distance;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
