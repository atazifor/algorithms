/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class PriorityQueue<Key> implements Iterable<Key> {

    private final Key[] a;
    private int N;

    PriorityQueue(int capacity) {
        a = (Key[]) new Object[capacity + 1]; // we want to start at index 1 not zero
        N = 0; // redundant
    }

    public void insert(Key key) {
        a[++N] = key;
        swim(N);
    }

    public Key delMax() {
        Key key = a[1];
        exch(1, N);
        a[N--] = null;
        sink(1);
        return key;
    }

    public int size() {
        return N;
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

    public Iterator<Key> iterator() {
        return new PriorityQueueIterator();
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
        return ((Comparable<Key>) a[i]).compareTo(a[j]) < 0;
    }

    // all children are definitely heaps of size 1.
    // so we look at level height -1 to 1
    public boolean isMaxHeap() {
        return isHeapOrdered(1);
    }

    private boolean isHeapOrdered(int k) {
        int left = 2 * k;
        int right = left + 1;
        if (left <= N && less(k, left)) return false;
        else if (right <= N && less(k, right)) return false;
        else return isHeapOrdered(left) && isHeapOrdered(right);
    }

    private void printQueue() {
        for (Iterator<Key> it = iterator(); it.hasNext(); ) {
            Key key = it.next();
            System.out.print(" > " + key);
        }
        System.out.println();
    }

    private class PriorityQueueIterator implements Iterator<Key> {
        PriorityQueue<Key> copy = null;

        public PriorityQueueIterator() {
            copy = new PriorityQueue<>(a.length);
            for (int i = 1; i <= size(); i++) {
                copy.insert(a[i]);
            }
        }

        public Key next() {
            if (hasNext()) return copy.delMax();
            throw new UnsupportedOperationException("queue is empty");
        }

        public boolean hasNext() {
            return copy.size() > 0;
        }

    }

    public static void main(String[] args) {

        PriorityQueue<Integer> q = new PriorityQueue<>(9);
        q.insert(4);
        q.insert(8);
        q.insert(1);
        q.insert(3);
        q.printQueue();
        System.out.println();
        q.delMax();
        q.delMax();
        q.insert(5);
        q.insert(10);
        q.printQueue();
    }
}
