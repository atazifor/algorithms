/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class ConnectedComponent {
    private int N; // number of components
    private int[] comoponents;
    private Graph graph;

    public ConnectedComponent(Graph graph) {
        this.graph = graph;
        comoponents = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            if (comoponents[i] == 0) {
                N++;
                bfs(i);
            }
        }
    }

    public int size() {
        return N;
    }

    public int id(int v) {
        return comoponents[v];
    }

    public boolean connected(int u, int v) {
        return id(u) == id(v);
    }

    private void bfs(int s) {
        boolean[] marked = new boolean[graph.V()];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            marked[v] = true;
            comoponents[v] = N;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean eulerCycle() {
        // every non-zero degree vertex must be connected
        int component = -1;
        for (int v = 0; v < graph.V(); v++) {
            int degree = graph.degree(v);
            if (degree > 0) {
                if (component == -1) component = id(v);
                else {
                    // we found a non-zero degree vertex that is not connected
                    if (id(v) != component) {
                        return false;
                    }
                }
            }
        }
        // degree of every vertex in the connected component must be even
        for (int v = 0; v < graph.V(); v++) {
            if (graph.degree(v) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(8, 9);
        System.out.println("== cc ==");
        ConnectedComponent dg = new ConnectedComponent(graph);
        System.out.println("dg.size() = " + dg.size());
        System.out.println("dg.connected(5,9) = " + dg.connected(5, 9));
        System.out.println("dg.connected(8,9) = " + dg.connected(8, 9));
        System.out.println("dg.eulerCycle() = " + dg.eulerCycle());

        graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        // graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        dg = new ConnectedComponent(graph);
        System.out.println("dg.eulerCycle() = " + dg.eulerCycle());
    }
}
