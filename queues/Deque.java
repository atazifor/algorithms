/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int front;
    private int rear;
    private int size;
    private Item[] deq;

    private static int DEFAULT_CAPACITY = 8;

    public Deque() {
        new Deque<>(DEFAULT_CAPACITY);
    }

    private Deque(int capacity) {
        front = -1;
        rear = -1;
        size = 0;
        deq = (Item[]) new Object[capacity];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("null items not supported");
        if (size == deq.length)
            resize(2 * deq.length);
        if (front == -1) {
            front = 0;
            rear = 0;
        }
        else if (front == 0) {
            front = deq.length - 1;
        }
        else {
            front--;
        }
        deq[front] = item;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("null items not supported");
        if (size == deq.length)
            resize(2 * deq.length);
        if (front == -1) {
            front = 0;
            rear = 0;
        }
        else if (rear == deq.length - 1) {
            rear = 0;
        }
        else {
            rear++;
        }
        deq[rear] = item;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = deq[front];
        size--;
        if (size == 0) {
            front = -1;
            rear = -1;
        }
        else if (front == deq.length - 1) {
            front = 0;
        }
        else {
            front++;
        }
        if (size == deq.length / 4) resize(deq.length / 2);
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = deq[rear];
        size--;
        if (size == 0) {
            front = -1;
            rear = -1;
        }
        else if (rear == 0) {
            rear = deq.length - 1;
        }
        else {
            rear--;
        }
        if (size == deq.length / 4) resize(deq.length / 2);
        return item;
    }

    public Iterator<Item> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<Item> {
        int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("deque is empty");
            }
            Item item = deq[(front + i) % deq.length];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    private void resize(int capacity) {
        assert capacity > size;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = deq[(front + i) % deq.length];
        }
        front = 0;
        rear = size - 1;
        deq = copy;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < deq.length; i++)
            s.append(" ").append(deq[i]);
        return s.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<>(3);
        deq.addFirst(2);
        deq.addFirst(5);
        deq.addLast(6);
        deq.removeFirst();
        deq.addLast(9);
        for (Iterator<Integer> it = deq.iterator(); it.hasNext(); ) {
            Integer i = it.next();
            System.out.println("i = " + i);
        }

        deq = new Deque<>(3);
        deq.addFirst(11);
        deq.addFirst(22);
        deq.removeFirst();
        deq.removeFirst();
        deq.addFirst(33);
        for (Iterator<Integer> it = deq.iterator(); it.hasNext(); ) {
            Integer j = it.next();
            System.out.println("j = " + j);
        }

        deq = new Deque<>(3);
        deq.addFirst(11);
        deq.addFirst(22);
        deq.removeFirst();
        deq.removeFirst();
        deq.addFirst(33);
        deq.addLast(44);
        deq.addFirst(32);
        deq.addLast(42);
        deq.addFirst(31);
        deq.addLast(41);
        for (Iterator<Integer> it = deq.iterator(); it.hasNext(); ) {
            Integer k = it.next();
            System.out.println("k = " + k);
        }

        System.out.println("deq = " + deq);
        deq.removeFirst();
        deq.removeLast();
        deq.removeFirst();
        deq.removeLast();
        deq.removeFirst();
        System.out.println("deq = " + deq);
    }
}
