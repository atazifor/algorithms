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
import java.util.Stack;

public class GraphWarmUp {

    public GraphWarmUp() {

    }

    /**
     * for any edge u->v, the order of vertices must be such that
     * u appears before v (topological order)
     * see that we can achieve this if we process all descendants of u and then process u last
     * [hence post order]
     *
     * @param graph
     * @return
     */
    public Iterable<String> topologicalSort(Graph graph) {
        Set<String> marked = new HashSet<>(graph.V());
        Stack<String> postOrder = new Stack<>();
        for (String u : graph.vertices()) {
            if (!marked.contains(u))
                dfsPostOrder(graph, u, marked, postOrder);
        }
        return postOrder;
    }

    public Iterable<String> dfsPostOrder(Graph graph, String s) {
        Set<String> marked = new HashSet<>(graph.V());
        List<String> postOrder = new ArrayList<>();
        dfsPostOrder(graph, s, marked, postOrder);
        Collections.reverse(postOrder);
        return postOrder;
    }

    public Iterable<String> dfsPostOrderUsingStack(Graph graph, String s) {
        Set<String> marked = new HashSet<>(graph.V());
        Stack<String> postOrder = new Stack<>();
        dfsPostOrder(graph, s, marked, postOrder);
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

    private void dfsPostOrder(Graph graph, String v, Set<String> marked, Stack<String> postOrder) {
        marked.add(v);
        for (String w : graph.adj(v)) {
            if (!marked.contains(w))
                dfsPostOrder(graph, w, marked, postOrder);
        }
        postOrder.push(v); // this is post order processing since descendants were processed first
    }

    public Iterable<String> topologicalSortWithCycleDetection(Graph graph) {
        Set<String> marked = new HashSet<>(graph.V());
        Stack<String> postOrder = new Stack<>();
        Set<String> cycleDetecter = new HashSet<>();
        for (String v : graph.vertices()) {
            if (!marked.contains(v)) {
                boolean isNonCyclic = dfsPostOrderCycleDetection(graph, v, marked, postOrder,
                                                                 cycleDetecter);
                if (!isNonCyclic) throw new RuntimeException("Cycle detected in the graph");
            }
        }
        return postOrder;
    }

    private boolean dfsPostOrderCycleDetection(Graph graph, String v, Set<String> marked,
                                               Stack<String> postOrder,
                                               Set<String> cycleDetector) {
        marked.add(v);
        cycleDetector.add(v);
        for (String w : graph.adj(v)) {
            if (cycleDetector.contains(w)) return false;
            if (!marked.contains(w)) {
                if (!dfsPostOrderCycleDetection(graph, w, marked, postOrder, cycleDetector)) {
                    return false;
                }
            }
        }
        cycleDetector.remove(v);
        postOrder.push(v);
        return true;
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

        public Iterable<String> vertices() {
            return graph.keySet();
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

        System.out.println("number of vertices = " + graph.V());
        System.out.println("number of edges = " + graph.E());

        GraphWarmUp graphWarmUp = new GraphWarmUp();

        Iterable<String> sorted = graphWarmUp.dfsPostOrder(graph, "A");
        System.out.println("== Post order of graph  ==");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]

        sorted = graphWarmUp.dfsPostOrderUsingStack(graph, "A");
        System.out.println("== Post order of graph using stack  ==");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]

        sorted = graphWarmUp.topologicalSort(graph);
        System.out.println("== Topological sort graph using stack  ==");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]
        sorted = graphWarmUp.topologicalSortWithCycleDetection(graph);
        System.out.println("== Topological sort graph using stack and Cycle detection ==");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]
        /** Create a cycle
         *     A
         *    / \
         *   B   C
         *  / \   \
         * D   E   F
         *          \
         *           A
         */
        graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "F");
        graph.addEdge("F", "A");
        sorted = graphWarmUp.dfsPostOrder(graph, "A");
        System.out.println("== Post order of graph with cycle ==");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]
        sorted = graphWarmUp.topologicalSortWithCycleDetection(graph);
        System.out.println("== Topological sort graph using stack and Cycle detection ==");
        System.out.println(sorted); // Output: [A, C, F, B, E, D]

    }
}
