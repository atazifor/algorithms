/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PriorityQueue<Key extends Comparable<Key>> {

    private final Key[] a;
    private int N;

    PriorityQueue(int capacity) {
        a = (Key[]) new Object[capacity + 1]; // we want to start at index 1 not zero
        N = 0; // redundant
    }

    public void insert(Key key) {
        a[N++] = key;
        swim(N);
    }

    public Key delMax() {
        Key key = a[1];
        exch(1, N);
        a[N--] = null;
        sink(1);
        return key;
    }

    public Key delRandom() {
        int k = StdRandom.uniformInt(1, N + 1);
        exch(1, k);
        return delMax();
    }

    public Key sample() {
        int k = StdRandom.uniformInt(1, N + 1);
        return a[k];
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) j++;
            if (less(k, j)) exch(k, j);
            else break;
            k = j;
        }
    }

    private void exch(int i, int j) {
        Key swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private boolean less(int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    public static void main(String[] args) {

    }
}
