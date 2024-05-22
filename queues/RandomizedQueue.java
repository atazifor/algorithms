/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static int DEFAULT_SIZE = 4;
    private Item[] items;
    private int n; // position of next element to be inserted

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[DEFAULT_SIZE];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("can't add null");
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException("can't dequeue empty queue");
        int i = StdRandom.uniformInt(n);
        Item item = items[i];
        items[i] = null;
        items[i] = items[--n]; // replace item to be removed with last item inserted
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) throw new NoSuchElementException("empty queue");
        int i = StdRandom.uniformInt(n);
        Item item = items[i];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int i;
        Item[] copy;

        public RandomizedQueueIterator() {
            i = n;
            copy = (Item[]) new Object[items.length];
            for (int k = 0; k < items.length; k++)
                copy[k] = items[k];
        }

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("queue is empty");
            int j = StdRandom.uniformInt(i);
            Item item = copy[j];
            copy[j] = null;
            copy[j] = copy[--i];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported operation");
        }

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < items.length; i++)
            s.append(" ").append(items[i]);
        return s.toString();
    }

    private void resize(int size) {
        assert size > n;
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(5);
        q.enqueue(2);
        q.enqueue(1);

        q.enqueue(3);
        q.enqueue(4);
        for (Iterator<Integer> it = q.iterator(); it.hasNext(); ) {
            System.out.println("it.next() = " + it.next());
        }
        StdOut.println("Sample " + q.sample());
        StdOut.println("Another Sample " + q.sample());
        StdOut.println("Dequeued " + q.dequeue());
        StdOut.println("Dequeued " + q.dequeue());
        for (Iterator<Integer> it = q.iterator(); it.hasNext(); ) {
            System.out.println("it.next() = " + it.next());
        }

        q = new RandomizedQueue<>();
        q.enqueue(5);
        q.enqueue(2);
        StdOut.println("Sample " + q.sample());
        StdOut.println("Another Sample " + q.sample());
        StdOut.println("Dequeued " + q.dequeue());
        StdOut.println("Dequeued " + q.dequeue());
        StdOut.println("queue " + q);

    }
}
