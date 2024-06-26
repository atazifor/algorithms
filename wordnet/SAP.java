/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description: Shortest Ancestral Path.
 * An ancestral path between two vertices v and w in a digraph is a directed path
 * from v to a common ancestor x, together with a directed path from w to the same ancestor x.
 * A shortest ancestral path is an ancestral path of minimum total length.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class SAP {
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int n = G.V();
        if (v < 0 || w < 0 || v >= n || w >= n)
            throw new IllegalArgumentException("args out of bounds");
        List<Integer> a = new ArrayList<>();
        if (v == w) return 0;
        a.add(v);
        List<Integer> b = new ArrayList<>();
        b.add(w);
        return length(a, b);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int n = G.V();
        if (v < 0 || w < 0 || v >= n || w >= n)
            throw new IllegalArgumentException("args out of bounds");
        if (v == w) return v;
        List<Integer> a = new ArrayList<>();
        a.add(v);
        List<Integer> b = new ArrayList<>();
        b.add(w);
        return ancestor(a, b);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateParams(v, w);
        return minAncestorDistance(v, w);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateParams(v, w);
        return minAncestorIndex(v, w);
    }

    private int[] bfsDistance(Iterable<Integer> s) {
        int n = G.V();
        int[] distance = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = -1; // not reachable initially
        }
        Queue<Integer> queue = new Queue<>();
        boolean[] marked = new boolean[n];
        for (int x : s) {
            queue.enqueue(x);
            marked[x] = true;
            distance[x] = 0;
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                    distance[w] = distance[v] + 1;
                }
            }
        }
        return distance;
    }

    private int minAncestorIndex(Iterable<Integer> v, Iterable<Integer> w) {
        int minDistance = Integer.MAX_VALUE; // or could use G.E()
        int minAncestor = -1;
        int n = G.V();
        int[] distanceA = bfsDistance(v);
        int[] distanceB = bfsDistance(w);
        for (int i = 0; i < n; i++) {
            if (distanceA[i] != -1
                    && distanceB[i] != -1) { // this implies vertex i is reachable from both v and w
                int distance = distanceA[i] + distanceB[i];
                if (distance < minDistance) {
                    minDistance = distance;
                    minAncestor = i;
                }
            }
        }
        return minAncestor;
    }

    private int minAncestorDistance(Iterable<Integer> v, Iterable<Integer> w) {
        int minAncestorIndex = minAncestorIndex(v, w);
        if (minAncestorIndex == -1) return -1;

        int[] distanceA = bfsDistance(v);
        int[] distanceB = bfsDistance(w);
        return distanceA[minAncestorIndex] + distanceB[minAncestorIndex];
    }

    private void validateParams(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("args can't be null");
        for (Integer x : v) {
            if (x == null) throw new IllegalArgumentException("an iterable can't have a null item");
            if (x < 0 || x >= G.V())
                throw new IllegalArgumentException("an iterable has an out of bound item");
        }
        for (Integer x : w) {
            if (x == null) throw new IllegalArgumentException("an iterable can't have a null item");
            if (x < 0 || x >= G.V())
                throw new IllegalArgumentException("an iterable has an out of bound item");
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        /*Integer[] v = new Integer[] { 13, 23, 24 };
        Integer[] w = new Integer[] { 6, 16, 17 };
        int ancestor = sap.ancestor(Arrays.asList(v), Arrays.asList(w));
        int length = sap.length(Arrays.asList(v), Arrays.asList(w));
        System.out.println("ancestor = " + ancestor);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        StdOut.printf("(%d, %d) length = %d, ancestor = %d\n", 13, 16, sap.length(13, 16),
                      sap.ancestor(13, 16));*/
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
