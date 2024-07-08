/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;

public class FlowNetwork {
    private final int V; // number  of vertices
    private Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;
        this.adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public static void main(String[] args) {

    }
}
