/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphWarmUp {

    public GraphWarmUp() {

    }

    public Iterable<String> dfsPostOrder(Graph graph, String s) {
        Set<String> marked = new HashSet<>(graph.V());
        List<String> postOrder = new ArrayList<>();
        dfsPostOrder(graph, s, marked, postOrder);
        Collections.reverse(postOrder);
        return postOrder;
    }

    private void dfsPostOrder(Graph graph, String v, Set<String> marked, List<String> postOrder) {
        marked.add(v);
        for (String w : graph.adj(v)) {
            if (!marked.contains(w))
                dfsPostOrder(graph, w, marked, postOrder);
        }
        postOrder.add(v); // this is post order processing since descendants were processed first
    }

    private static class Graph {

        public Graph() {

        }

        private final Map<String, List<String>> graph = new HashMap<>();

        public void addEdge(String u, String v) {
            if (graph.get(u) == null) graph.put(u, new ArrayList<>());
            graph.get(u).add(v);
        }

        /**
         * neighbors of vertex v
         *
         * @param v
         * @return
         */
        public List<String> adj(String v) {
            if (graph.get(v) != null) return graph.get(v);
            else return new ArrayList<>();
        }

        public int degree(String v) {
            return adj(v).size();
        }

        /**
         * number of vertices
         *
         * @return
         */
        public int V() {
            return graph.keySet().size();
        }

        /**
         * number of edges
         *
         * @return
         */
        public int E() {
            int sum = 0;
            for (String v : graph.keySet()) {
                sum += degree(v);
            }
            return sum;
        }
    }


    public static void main(String[] args) {
        /**
         *     A
         *    / \
         *   B   C
         *  / \   \
         * D   E   F
         */
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "F");

        GraphWarmUp graphWarmUp = new GraphWarmUp();

        Iterable<String> sorted = graphWarmUp.dfsPostOrder(graph, "A");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]
    }
}
