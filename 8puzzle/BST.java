/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class BST<Key extends Comparable<Key>, Value> {
    Node root;

    public BST() {

    }

    public Value search(Key key) {
        return search(root, key);
    }

    private Value search(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x.val;
        else if (cmp < 0) return search(x.left, key);
        else return search(x.right, key);
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp == 0) x.val = val;
        else if (cmp < 0) x.left = put(x.left, key, val);
        else x.right = put(x.right, key, val);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key floor(Key key) {
        return floor(root, key);
    }

    public Key ceiling(Key key) {
        return ceiling(root, key);
    }


    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        inorder(root, queue);
        return queue;
    }

    private Key floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return key;
        else if (cmp < 0) return floor(x.left, key);
        else {
            Key t = floor(x.right, key);
            if (t != null) return t;
            else return x.key;
        }
    }

    private Key ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp > 0) return ceiling(x.right, key);
        else {
            Key t = ceiling(x.left, key);
            if (t != null) return t;
            else return x.key;
        }
    }

    /*
    number keys less than key
     */
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return size(x.left);
        else if (cmp < 0) return rank(x.left, key);
        else {
            return size(x.left) + 1 + rank(x.right, key);
        }
    }

    public void printInOrder() {
        for (Key key : keys()) System.out.print(" " + key);
    }

    public Node min() {
        return min(root);
    }

    public void delMin() {
        // need to re-assign  root because may be root is the  min
        root = delMin(root);
    }

    /**
     * returns the root of the sub-tree node (node the node that was deleted)
     *
     * @param x root of sub-tree to delete
     * @return
     */
    private Node delMin(Node x) {
        if (x == null) return null;
        if (x.left == null) return x.right;
        else x.left = delMin(x.left);
        // update count
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node del(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = del(x.left, key);
        else if (cmp > 0) x.right = del(x.right, key);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Node t = x;
            x = min(t.right);
            x.right = delMin(t.right);
            x.left = t.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node min(Node x) {
        if (x == null) return null;
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max() {
        return max(root);
    }

    private Key max(Node x) {
        if (x == null) return null;
        if (x.right == null) return x.key;
        else return max(x.right);
    }


    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }


    public static void main(String[] args) {
        BST<Integer, Integer> bst = new BST<>();
        for (int i = 0; i < 15; i++) {
            int x = StdRandom.uniformInt(1, 50);
            bst.put(x, x);
        }
        bst.printInOrder();
        System.out.println();
        System.out.println("bst.size() = " + bst.size());
        System.out.println("min() = " + bst.min());
        System.out.println("max() = " + bst.max());
        int floor = bst.max() - 10;
        int ceiling = (bst.min().key + bst.max()) / 2;
        System.out.println("floor(" + floor + ") = " + bst.floor(floor));
        System.out.println("ceiling(" + ceiling + ") = " + bst.ceiling(ceiling));
        System.out.println("rank(" + ceiling + ") = " + bst.rank(ceiling));
    }

    public boolean isBst() {
        // use what ever is is absolute min and max for the key.
        // for Integer, it's Integer.MIN_VALUE, Integer.MAX_VALUE
        return isBst(root, null, null);
    }

    private boolean isBst(Node x, Key min, Key max) {
        if (x == null) return true;
        if (x.key.compareTo(min) > 0 && x.key.compareTo(max) < 0)
            return isBst(x.left, min, x.key) && isBst(x.right, x.key, max);
        else return false;
    }

    private class Node {
        Key key;
        Value val;
        Node left;
        Node right;
        int size; // size of subtree containing this node.

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

    }
}
