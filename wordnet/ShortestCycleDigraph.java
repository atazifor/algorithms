/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.HashSet;
import java.util.Set;

public class ShortestCycleDigraph {
    public ShortestCycleDigraph() {

    }

    public boolean isCyclic(Graph graph) {
        Set<String> marked = new HashSet<>();
        Set<String> stack = new HashSet<>();// holds vertices that are still in the dfs stack
        // Map<String, String> edgeTo = new HashMap<>();
        return dfsCycle(graph, graph.vertices().iterator().next(), marked, stack);
    }

    private boolean dfsCycle(Graph graph, String u, Set<String> marked, Set<String> stack) {
        marked.add(u);
        stack.add(u);
        for (String v : graph.adj(u)) {
            if (stack.contains(v)) return true; // we found a cycle
            if (!marked.contains(v)) {
                if (!dfsCycle(graph, v, marked, stack)) return true;
            }
        }
        stack.remove(u); // done processing vertex u
        return false;
    }

    public static void main(String[] args) {
        /** Create a cycle
         *     A
         *    / \
         *   B   C
         *  / \   \
         * D   E   F
         *          \
         *           A
         */
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "F");
        graph.addEdge("F", "A");
        ShortestCycleDigraph scg = new ShortestCycleDigraph();
        boolean cycle = scg.isCyclic(graph);
        System.out.println("== Is Cyclic ==");
        System.out.println(cycle); // Output: [A, C, F, B, E, D]
    }
}
