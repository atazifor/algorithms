/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<Item> implements Iterable<Item> {
    private Node front;
    private Node rear;

    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public LinkedListDeque() {
        size = 0;
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
        if (size == 0) {
            Node node = new Node();
            node.item = item;
            front = node;
            rear = node;
        }
        else {
            Node oldFront = front;
            front = new Node();
            front.item = item;
            front.next = oldFront;
            oldFront.prev = front;
            // oldFront = null;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("null items not supported");
        if (size == 0) {
            Node node = new Node();
            node.item = item;
            front = node;
            rear = node;
        }
        else {
            Node oldRear = rear;
            rear = new Node();
            rear.item = item;
            rear.prev = oldRear;
            oldRear.next = rear;
            // oldRear = null;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = front.item;
        front = front.next;
        if (front != null) {
            front.prev = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = rear.item;
        rear = rear.prev;
        if (rear != null) {
            rear.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<Item> {
        Node current = front;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("deque is empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }


    public String toString() {
        Node current = front;
        StringBuilder s = new StringBuilder();
        while (current != null) {
            s.append(" ").append(current.item);
            current = current.next;
        }
        return s.toString();
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> deq = new LinkedListDeque<>();
        deq.addFirst(2);
        deq.addFirst(5);
        deq.addLast(6);
        deq.removeFirst();
        deq.addLast(9);
        for (Iterator<Integer> it = deq.iterator(); it.hasNext(); ) {
            Integer i = it.next();
            System.out.println("i = " + i);
        }

        deq = new LinkedListDeque<>();
        deq.addFirst(11);
        deq.addFirst(22);
        deq.removeFirst();
        deq.removeFirst();
        deq.addFirst(33);
        for (Iterator<Integer> it = deq.iterator(); it.hasNext(); ) {
            Integer j = it.next();
            System.out.println("j = " + j);
        }

        deq = new LinkedListDeque<>();
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
