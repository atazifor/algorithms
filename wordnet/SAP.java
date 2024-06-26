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
        List<Integer> a = new ArrayList<>();
        a.add(v);
        List<Integer> b = new ArrayList<>();
        b.add(w);
        return ancestor(a, b);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("args can't be null");
        for (Integer x : v) {
            if (v == null) throw new IllegalArgumentException("an iterable can't have a null item");
            if (x < 0 || x >= G.V())
                throw new IllegalArgumentException("an iterable has an out of bound item");
        }
        for (Integer x : w) {
            if (v == null) throw new IllegalArgumentException("an iterable can't have a null item");
            if (x < 0 || x >= G.V())
                throw new IllegalArgumentException("an iterable has an out of bound item");
        }
        return bfsAncestor(v, w).distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("args can't be null");
        for (Integer x : v) {
            if (v == null) throw new IllegalArgumentException("an iterable can't have a null item");
            if (x < 0 || x >= G.V())
                throw new IllegalArgumentException("an iterable has an out of bound item");
        }
        for (Integer x : w) {
            if (v == null) throw new IllegalArgumentException("an iterable can't have a null item");
            if (x < 0 || x >= G.V())
                throw new IllegalArgumentException("an iterable has an out of bound item");
        }
        return bfsAncestor(v, w).ancestor;
    }

    private class Result {
        int ancestor;
        int distance;

        public Result() {
            ancestor = -1;
            distance = -1;
        }
    }

    private Result bfsAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        Result result = new Result();

        Queue<Integer> queueA = new Queue<>();
        Queue<Integer> queueB = new Queue<>();

        boolean[] markedA = new boolean[G.V()];
        boolean[] markedB = new boolean[G.V()];

        int[] distanceA = new int[G.V()];
        int[] distanceB = new int[G.V()];

        for (Integer x : v) {
            queueA.enqueue(x);
            markedA[x] = true;
            distanceA[x] = 0;
        }

        for (Integer x : w) {
            queueB.enqueue(x);
            markedB[x] = true;
            distanceB[x] = 0;
        }

        while (!queueA.isEmpty() || !queueB.isEmpty()) {
            if (!queueA.isEmpty()) {
                int xA = queueA.dequeue();
                for (int yA : G.adj(xA)) {
                    if (markedB[yA]) {
                        result.ancestor = yA;
                        result.distance = distanceA[xA] + 1 + distanceB[yA];
                        return result;
                    }
                    else if (!markedA[yA]) {
                        queueA.enqueue(yA);
                        markedA[yA] = true;
                        distanceA[yA] = distanceA[xA] + 1;
                    }
                }
            }
            if (!queueB.isEmpty()) {
                int xB = queueB.dequeue();
                for (int yB : G.adj(xB)) {
                    if (markedA[yB]) {
                        result.ancestor = yB;
                        result.distance = distanceB[xB] + 1 + distanceA[yB];
                        return result;
                    }
                    else if (!markedB[yB]) {
                        queueB.enqueue(yB);
                        markedB[yB] = true;
                        distanceB[yB] = distanceB[xB] + 1;
                    }
                }
            }
        }
        return result;
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
