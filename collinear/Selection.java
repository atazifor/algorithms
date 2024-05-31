/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Find smallest kth element in an array
 */
public class Selection {

    public Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length - 1;
        while (lo < hi) {
            int j = partition(a, lo, hi);
            if (k < j) hi = j - 1;
            else if (k > j) lo = j + 1;
            return a[k];
        }
        return null;
    }

    private void sort3Way(Comparable[] a, int lo, int hi) {
        int lt = lo;
        int gt = hi;
        int i = lo;
        Comparable v = a[lo];
        while (i <= gt) {
            int comp = a[i].compareTo(v);
            if (comp < 0) exch(a, lt++, i++);
            else if (comp > 0) exch(a, i, gt--);
            else i++;
        }
        sort3Way(a, lo, lt - 1);
        sort3Way(a, gt + 1, hi);
    }

    public void sort(Comparable[] a) {
        StdRandom.shuffle(a); // we must shuffle
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;

        while (true) {
            while (less(a[++i], a[lo]))
                if (i == hi) break;
            while (less(a[lo], a[--j]))
                if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        // we found right position of j
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[] { 2, 8, 1, 4, 9, 0, 5, 2, 1, 2 };
        Selection selection = new Selection();
        selection.sort(a);
        for (Integer i : a) {
            System.out.print(" " + i);
        }
        StdOut.println();

        a = new Integer[] { 2, 8, 1, 4, 9, 0, 5 };
        Comparable thirdSmallest = selection.select(a, 3);
        System.out.println("thirdSmallest = " + thirdSmallest);
    }
}
