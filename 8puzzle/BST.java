/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

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

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp == 0) x.val = val;
        else if (cmp < 0) x.left = put(x.left, key, val);
        else x.right = put(x.right, key, val);
        return x;
    }

    public Key floor(Key key) {
        return floor(root, key);
    }

    public Key ceiling(Key key) {
        return ceiling(root, key);
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

    public void printInOrder() {
        inOrder(root);
    }

    public Key min() {
        return min(root);
    }

    private Key min(Node x) {
        if (x == null) return null;
        if (x.left == null) return x.key;
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

    private void inOrder(Node x) {
        if (x == null) return;
        inOrder(x.left);
        System.out.print(" " + x.val);
        inOrder(x.right);
    }

    public static void main(String[] args) {
        BST<Integer, Integer> bst = new BST<>();
        for (int i = 0; i < 15; i++) {
            int x = StdRandom.uniformInt(1, 50);
            bst.put(x, x);
        }
        bst.printInOrder();
        System.out.println();
        System.out.println("min() = " + bst.min());
        System.out.println("max() = " + bst.max());
        int floor = bst.max() - 10;
        int ceiling = (bst.min() + bst.max()) / 2;
        System.out.println("floor(" + floor + ") = " + bst.floor(floor));
        System.out.println("ceiling(" + ceiling + ") = " + bst.ceiling(ceiling));
    }

    private class Node {
        Key key;
        Value val;
        Node left;
        Node right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }

    }
}