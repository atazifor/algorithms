/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] q;
    public int front; // Index of the front element
    public int rear;  // Index of next element to insert
    int size;

    public ResizingArrayQueue(int capacity) {
        q = (Item[]) new Object[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Item item) {
        // Check for overflow
        if (size == q.length) {
            resize(2 * q.length);
        }

        q[rear] = item;
        size++;
        rear = (rear + 1) % q.length; // Circular behavior
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        Item item = q[front++];
        if (front == q.length) front = 0;
        size--;
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int i = 0;
        if (front <= rear) {
            for (int j = front; j <= rear; j++)
                copy[i++] = q[j];
        }
        else {
            for (int j = front; j < q.length; j++)
                copy[i++] = q[j];
            int remainingItems = size - i;
            for (int j = 0; j < remainingItems; j++)
                copy[i++] = q[j];
        }
        q = copy;
        front = 0;
        rear = size;
    }

    public Iterator<Item> iterator() {
        return new ArrayQueueIterator();
    }

    private class ArrayQueueIterator implements Iterator<Item> {
        int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new UnsupportedOperationException("can't iterate. queue is empty");
            }
            Item item = q[(i + front) % q.length];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }

    }

    public static void main(String[] args) {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<Integer>(4);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.println("front = " + q.front);
        System.out.println("rear = " + q.rear);
        q.enqueue(4);
        q.dequeue();
        System.out.println("front = " + q.front);
        System.out.println("rear = " + q.rear);
        q.enqueue(5);
        System.out.println("front = " + q.front);
        System.out.println("rear = " + q.rear);

        q.dequeue();
        q.dequeue();
        q.dequeue();
        System.out.println("a.front = " + q.front);
        System.out.println("a.rear = " + q.rear);
        q.dequeue();
        System.out.println("b.front = " + q.front);
        System.out.println("b.rear = " + q.rear);

    }


}
