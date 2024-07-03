/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 * Given an edge-weighted digraph and let P be a shortest path from vertex
s to vertex t. Design an ElogV algorithm to find a path (not necessarily simple) other than P from
s to  t that is as short as possible. Assume all of the edge weights are strictly positive.
 **************************************************************************** */

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class SecondShortestPath {
    private double[] disTo;

    private DirectedEdge[] edgeTo;

    private IndexMinPQ<Double> pq;
    private final EdgeWeightedDigraph G;

    public SecondShortestPath(EdgeWeightedDigraph G, int s) {
        this.G = G;
        disTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        pq = new IndexMinPQ<>(G.E());
        for (int i = 0; i < G.V(); i++)
            disTo[i] = Double.POSITIVE_INFINITY;
        disTo[s] = 0;
        pq.insert(s, disTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }

    }

    public Iterable<DirectedEdge> path(int v) {
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public double disTo(int v) {
        return disTo[v];
    }

    public void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (disTo[w] > disTo[v] + e.weight()) {
            edgeTo[w] = e;
            disTo[w] = disTo[v] + e.weight();
            if (pq.contains(w))
                pq.decreaseKey(w, e.weight());
            else
                pq.insert(w, e.weight());
        }
    }

    public Iterable<DirectedEdge> secondShortestPath(int t) {
        EdgeWeightedDigraph Gr = new EdgeWeightedDigraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                Gr.addEdge(new DirectedEdge(e.to(), e.from(), e.weight()));
            }
        }
        SecondShortestPath ssp = new SecondShortestPath(Gr, t);
        double shortestDis = disTo[t];
        double nextShortesDis = Double.POSITIVE_INFINITY;
        DirectedEdge nextEdge = null;
        for (DirectedEdge e : G.edges()) {
            int v = e.from();
            int w = e.to();
            double dis = this.disTo(v) + e.weight() + ssp.disTo(w);
            if (shortestDis < dis && dis < nextShortesDis) {
                nextShortesDis = dis;
                nextEdge = e;
            }
        }
        SecondShortestPath ssp2 = new SecondShortestPath(G, nextEdge.to());
        List<DirectedEdge> first = new ArrayList<>();
        for (DirectedEdge e : this.path(nextEdge.from()))
            first.add(e);
        first.add(nextEdge);
        for (DirectedEdge e : ssp2.path(t)) {
            first.add(e);
        }
        return first;
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

        SecondShortestPath msp = new SecondShortestPath(G, 0);
        for (int i = 0; i < G.V(); i++) {
            StdOut.printf("disTo(%s): %f | path: %S\n", i, msp.disTo[i], msp.printPath(i));
        }

        StdOut.printf("secondDisTo(%s): %f | path: %S\n", 5, msp.disTo[5],
                      msp.secondShortestPath(5));
    }
}
