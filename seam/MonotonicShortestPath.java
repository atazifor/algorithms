/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 *      Given an edge-weighted digraph
 *      G, design an ElogE algorithm to find a monotonic shortest path from
 *      s to every other vertex. A path is monotonic if the sequence of edge weights along
 *      the path are either strictly increasing or strictly decreasing.
 **************************************************************************** */

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class MonotonicShortestPath {
    private double[] disTo; // shortest known distance from s to v
    private DirectedEdge[] edgeTo; // last edge on shortest path from s to v

    private MinPQ<DirectedEdge> pq;

    public MonotonicShortestPath(EdgeWeightedDigraph G, int s) {
        disTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        for (int i = 0; i < G.V(); i++)
            disTo[i] = Double.POSITIVE_INFINITY;
        disTo[s] = 0;

        pq = new MinPQ<>(new Comparator<DirectedEdge>() {
            public int compare(DirectedEdge o1, DirectedEdge o2) {
                return Double.compare(o1.weight(), o2.weight());
            }
        });
        for (DirectedEdge e : G.edges()) {
            pq.insert(e);
        }
        while (!pq.isEmpty())
            relax(pq.delMin());

    }

    // distance from s to v
    public double disTo(int v) {
        return disTo[v];
    }

    // path from s to v
    public Iterable<DirectedEdge> path(int v) {
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (disTo[v] < Double.POSITIVE_INFINITY && disTo[w] > disTo[v] + e.weight()) {
            disTo[w] = disTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public String printPath(int v) {
        StringBuilder sb = new StringBuilder();
        for (DirectedEdge e : path(v)) {
            sb.append(e + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(6);
        G.addEdge(new DirectedEdge(0, 1, 1));
        G.addEdge(new DirectedEdge(0, 2, 4));
        G.addEdge(new DirectedEdge(1, 2, 2));
        G.addEdge(new DirectedEdge(1, 3, 5));
        G.addEdge(new DirectedEdge(2, 3, 3));
        G.addEdge(new DirectedEdge(2, 4, 7));
        G.addEdge(new DirectedEdge(3, 4, 6));
        G.addEdge(new DirectedEdge(4, 5, 8));
        G.addEdge(new DirectedEdge(3, 5, 9));

        MonotonicShortestPath msp = new MonotonicShortestPath(G, 0);
        for (int i = 0; i < G.V(); i++) {
            StdOut.printf("disTo(%s): %f | path: %S\n", i, msp.disTo[i], msp.printPath(i));
        }
    }
}
