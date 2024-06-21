/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;

public class Center {
    private Graph graph;

    public Center(Graph graph) {
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

    public List<Integer> longestPath() {
        Result one = dfs(0);
        Result two = dfs(one.node);

        int[] edgeTo = new int[graph.V()];
        boolean[] marked = new boolean[graph.V()];
        Stack<Integer> stack = new Stack<>();
        stack.push(two.node);
        edgeTo[two.node] = -1;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            marked[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    stack.push(w);
                    edgeTo[w] = v;
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        for (int x = one.node; x != two.node; x = edgeTo[x]) {
            path.add(x);
        }
        return path;
    }

    public List<Integer> center() {
        List<Integer> path = longestPath();
        int n = path.size();
        List<Integer> result = new ArrayList<>();
        if (n % 2 == 0) {
            result.add(path.get(n / 2 - 1));
        }
        result.add(path.get(n / 2));
        return result;
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
        Center dg = new Center(graph);
        System.out.println("diameter = " + dg.longestPath());
        System.out.println("dg.center() = " + dg.center());
    }
}
