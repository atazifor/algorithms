/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class MyDijkstra {
    private final double[] disTo;
    private final DirectedEdge[] edgeTo;
    private final IndexMinPQ<Double> minPQ;

    public MyDijkstra(EdgeWeightedDigraph graph, int s) {
        edgeTo = new DirectedEdge[graph.V()];
        disTo = new double[graph.V()];
        for (int i = 0; i < graph.V(); i++)
            disTo[i] = Double.POSITIVE_INFINITY;
        disTo[s] = 0.0;
        minPQ = new IndexMinPQ<>(graph.V());
        minPQ.insert(s, disTo[s]);
        while (!minPQ.isEmpty()) {
            int v = minPQ.delMin();  //add to tree closest  non-tree vertex
            for (DirectedEdge e : graph.adj(v)) {  //relax all its edges
                relax(e);
            }
        }

    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (disTo[w] > disTo[v] + e.weight()) {
            disTo[w] = disTo[v] + e.weight();
            edgeTo[w] = e;
            if (minPQ.contains(w))
                minPQ.decreaseKey(w, disTo[w]);
            else
                minPQ.insert(w, disTo[w]);
        }
    }

    public double disTo(int v) {
        return disTo[v];
    }

    public Iterable<DirectedEdge> path(int v) {
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(8);
        graph.addEdge(new DirectedEdge(0, 1, 5));
        graph.addEdge(new DirectedEdge(0, 4, 9));
        graph.addEdge(new DirectedEdge(0, 7, 8));
        graph.addEdge(new DirectedEdge(1, 3, 15));
        graph.addEdge(new DirectedEdge(1, 2, 12));
        graph.addEdge(new DirectedEdge(1, 7, 4));
        graph.addEdge(new DirectedEdge(2, 3, 3));
        graph.addEdge(new DirectedEdge(2, 6, 11));
        graph.addEdge(new DirectedEdge(3, 6, 9));
        graph.addEdge(new DirectedEdge(4, 5, 4));
        graph.addEdge(new DirectedEdge(4, 7, 5));
        graph.addEdge(new DirectedEdge(4, 6, 20));
        graph.addEdge(new DirectedEdge(5, 2, 1));
        graph.addEdge(new DirectedEdge(5, 6, 13));
        graph.addEdge(new DirectedEdge(7, 2, 7));
        graph.addEdge(new DirectedEdge(7, 5, 6));

        MyDijkstra myDijkstra = new MyDijkstra(graph, 0);
        System.out.println("disTo(6) = " + myDijkstra.disTo(6));
        System.out.println("path(6) = " + myDijkstra.path(6));
    }
}
