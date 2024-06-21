/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class UndirectedGraph {
    boolean[] marked;
    int[] edgeTo;

    public UndirectedGraph() {
        int n = 7;
        marked = new boolean[n];
        edgeTo = new int[n];
        for (int i = 0; i < n; i++) {
            edgeTo[i] = -1;
        }
    }

    public void dfs(Graph graph, int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            marked[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    stack.push(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }

    public void dfsRecursive(Graph graph, int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w);
                edgeTo[w] = v;
            }
        }
    }

    public void bfs(Graph graph, int s) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            marked[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    edgeTo[w] = v;
                }
            }
        }
    }

    public void bfs2(Graph graph, int s) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[v] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }

    public Iterable<Integer> path(int s, int v) {
        if (!marked[v]) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public void printEdgeTo() {
        for (int i = 0; i < edgeTo.length; i++) System.out.println(i + "-" + edgeTo[i]);
    }

    public static void main(String[] args) {
        Graph graph = new Graph(7);
        graph.addEdge(0, 3);
        graph.addEdge(0, 3);
        graph.addEdge(0, 5);
        graph.addEdge(1, 5);
        graph.addEdge(5, 4);
        graph.addEdge(5, 2);
        graph.addEdge(3, 6);
        System.out.println("== dfs linear ==");
        UndirectedGraph ug = new UndirectedGraph();
        ug.dfs(graph, 0);
        System.out.println("path(0, 6) = " + ug.path(0, 6));
        System.out.println("path(0, 2) = " + ug.path(0, 2));
        System.out.println("path(0, 4) = " + ug.path(0, 4));

        System.out.println("== dfs recursive ==");
        ug = new UndirectedGraph();
        ug.dfsRecursive(graph, 0);
        System.out.println("path(0, 6) = " + ug.path(0, 6));
        System.out.println("path(0, 2) = " + ug.path(0, 2));
        System.out.println("path(0, 4) = " + ug.path(0, 4));

        System.out.println("== bfs ==");
        ug = new UndirectedGraph();
        ug.bfs(graph, 1);
        ug.printEdgeTo();
        System.out.println("path(1, 6) = " + ug.path(1, 6));
        System.out.println("path(1, 2) = " + ug.path(1, 2));
        System.out.println("path(1, 4) = " + ug.path(1, 4));

        System.out.println("== bfs 2 ==");
        ug = new UndirectedGraph();
        ug.bfs2(graph, 1);
        ug.printEdgeTo();
        System.out.println("path(1, 6) = " + ug.path(1, 6));
        System.out.println("path(1, 2) = " + ug.path(1, 2));
        System.out.println("path(1, 4) = " + ug.path(1, 4));
    }
}
