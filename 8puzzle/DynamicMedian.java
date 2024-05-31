/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

public class DynamicMedian<Key extends Comparable<Key>> {
    private MaxPQ<Key> maxPQ;
    private MinPQ<Key> minPQ;

    private int size;

    public DynamicMedian() {
        maxPQ = new MaxPQ<>();
        minPQ = new MinPQ<>();
    }

    public void insert(Key x) {
        if (minPQ.isEmpty()) minPQ.insert(x);
        else if (x.compareTo(minPQ.min()) > 0) {
            minPQ.insert(x);
            if (minPQ.size() - maxPQ.size() > 1) maxPQ.insert(minPQ.delMin());
        }
        else {
            maxPQ.insert(x);
            if (maxPQ.size() - minPQ.size() > 1) minPQ.insert(maxPQ.delMax());
        }
    }

    public Key findMedian() {
        int maxSize = maxPQ.size();
        int minSize = minPQ.size();
        if (maxSize == minSize) {
            if (maxPQ.max().compareTo(minPQ.min()) < 0) return maxPQ.max();
            else return minPQ.min();
        }
        else if (maxSize > minSize) return maxPQ.max();
        else return minPQ.min();
    }

    public void delMedian() {
        int maxSize = maxPQ.size();
        int minSize = minPQ.size();
        if (maxSize == minSize) {
            if (maxPQ.max().compareTo(minPQ.min()) < 0) maxPQ.delMax();
            else minPQ.delMin();
        }
        else if (maxSize > minSize) {
            maxPQ.delMax();
        }
        else {
            minPQ.delMin();
        }
    }


    public static void main(String[] args) {
        DynamicMedian dynamicMedian = new DynamicMedian();
        dynamicMedian.insert(4);
        dynamicMedian.insert(2);
        dynamicMedian.insert(3);
        dynamicMedian.insert(8);
        dynamicMedian.insert(5);
        dynamicMedian.insert(1);
        dynamicMedian.insert(9);
        dynamicMedian.insert(6);
        System.out.println("Median 1 = " + dynamicMedian.findMedian());
        dynamicMedian.delMedian();
        System.out.println("Median 2 = " + dynamicMedian.findMedian());
        dynamicMedian.delMedian();
        System.out.println("Median 3 = " + dynamicMedian.findMedian());
    }
}
