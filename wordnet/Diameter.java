/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class Diameter {
    private Graph graph;

    public Diameter(Graph graph) {
        this.graph = graph;
    }

    private class Result {
        int distance;
        int node;

        public Result(int distance, int node) {
            this.distance = distance;
            this.node = node;
        }
    }

    private Result dfs(int s) {
        Stack<int[]> stack = new Stack<>(); // holds node and distance from s
        boolean[] marked = new boolean[graph.V()];
        stack.push(new int[] { s, 0 });
        int maxDistance = 0;
        int maxNode = s;
        while (!stack.isEmpty()) {
            int[] v = stack.pop();
            marked[v[0]] = true;
            if (v[1] > maxDistance) {
                maxDistance = v[1];
                maxNode = v[0];
            }
            for (int w : graph.adj(v[0])) {
                if (!marked[w]) {
                    stack.push(new int[] { w, v[1] + 1 });
                }
            }
        }
        return new Result(maxDistance, maxNode);
    }

    public int diameter() {
        Result one = dfs(0);
        Result two = dfs(one.node);
        return two.distance;
    }

    public static void main(String[] args) {
        // {0, 1}, {0, 2}, {1, 3}, {2, 4}, {2, 5}, {5, 6}, {5, 7}
        Graph graph = new Graph(8);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        System.out.println("== dfs linear ==");
        Diameter dg = new Diameter(graph);
        System.out.println("diameter = " + dg.diameter());
    }
}
