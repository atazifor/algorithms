/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class MatchNutsBolts {

    public void matchPairs(Comparable[] nuts, Comparable[] bolts) {
        StdRandom.shuffle(nuts);
        StdRandom.shuffle(bolts);
        mathPairs(nuts, bolts, 0, nuts.length - 1);
    }

    private void mathPairs(Comparable[] nuts, Comparable[] bolts, int lo, int hi) {
        if (hi <= lo) return; // it's already matched
        int j = partition(nuts, lo, hi, bolts[lo]);
        partition(bolts, lo, hi, nuts[j]);
        mathPairs(nuts, bolts, lo, j - 1);
        mathPairs(nuts, bolts, j + 1, hi);
    }

    private int partition(Comparable[] a, int lo, int hi, Comparable pivot) {
        int lt = lo;
        int gt = hi;
        int i = lo;
        while (i <= gt) {
            if (a[i].compareTo(pivot) < 0) exch(a, lt++, i++);
            else if (a[i].compareTo(pivot) > 0) exch(a, i, gt--);
            else i++;
        }
        return gt;
    }

    private void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void printArray(Comparable[] a) {
        for (Comparable c : a) {
            System.out.print(" " + c);
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Character nuts[] = { '@', '#', '$', '%', '^', '&' };
        Character bolts[] = { '$', '%', '&', '^', '@', '#' };

        MatchNutsBolts matchNutsBolts = new MatchNutsBolts();
        // Method based on quick sort which matches nuts and bolts
        matchNutsBolts.matchPairs(nuts, bolts);

        System.out.println("Matched nuts and bolts are : ");
        printArray(nuts);
        printArray(bolts);

        Integer[] integerNuts = { 2, 4, 1, 3, 5 };
        Integer[] integerBolts = { 5, 2, 4, 1, 3 };
        matchNutsBolts.matchPairs(integerNuts, integerBolts);
        System.out.println("Matched integer nuts and bolts are : ");
        printArray(integerNuts);
        printArray(integerBolts);
    }
}
