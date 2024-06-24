/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description: Find Directed cycle with the minimum number of edges (or report that the graph is acyclic) in a digraph
 *
 *  Global Visited Set:
    globalVisited is used to ensure each node is visited only once across all DFS calls.
    DFS Cycle Detection:

    For each node in the graph, perform DFS to detect cycles.
    Use local visited, stack, and parent sets/maps for each DFS call to ensure independence.
    Add detected cycles to detectedCycles.
    BFS for Shortest Cycle:

    For each detected cycle, use BFS to find the shortest path within the cycle.
    Track the shortest cycle length among all detected cycles.
    Reset Data Structures:

    Local visited, stack, and parent sets/maps are reset for each DFS call.
 **************************************************************************** */


import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShortestCycleDigraph {
    public ShortestCycleDigraph() {

    }

    public List<String> cycle(Graph graph) {
        List<List<String>> dfsCycles = new ArrayList<>();
        Set<String> globalVisited = new HashSet<>();
        for (String s : graph.vertices()) {
            Set<String> stack = new HashSet<>();// holds vertices that are still in the dfs stack
            Map<String, String> edgeTo = new HashMap<>();
            Set<String> marked = new HashSet<>();
            edgeTo.put(s, "SENTINEL");
            if (!globalVisited.contains(s)) {
                System.out.println("Called = " + s);
                dfsCycle(graph, s, marked, stack, edgeTo, dfsCycles);
                globalVisited.addAll(marked);
            }
        }

        if (dfsCycles.size() == 0) {
            System.out.println("Graph has no cycles");
            return new ArrayList<>();
        }

        List<String> cycle = null; // shortest cycle
        System.out.println("dfsCycle = " + dfsCycles);
        for (List<String> dfsCycle : dfsCycles) {
            // iterate through each node that is part of the cycle
            // first and last node are same, so no need to look at last node, hence [i < dfsCycle.size() - 1]
            for (int i = 0; i < dfsCycle.size() - 1; i++) {
                String x = dfsCycle.get(i);
                List<String> bfsCycle = new ArrayList<>();
                bfs(graph, x, bfsCycle);
                if (cycle == null) cycle = bfsCycle;
                else {
                    if (cycle.size() > bfsCycle.size()) cycle = bfsCycle;
                }
            }
        }
        return cycle;
    }

    private boolean dfsCycle(Graph graph, String u, Set<String> marked, Set<String> stack,
                             Map<String, String> edgeTo, List<List<String>> dfsCycles) {
        marked.add(u);
        stack.add(u);
        for (String v : graph.adj(u)) {
            if (stack.contains(v)) {
                List<String> dfsCycle = new ArrayList<>();
                dfsCycle.add(v);
                String x = u;
                for (; edgeTo.get(x) != "SENTINEL"; x = edgeTo.get(x)) {
                    dfsCycle.add(x);
                }
                dfsCycle.add(x);
                dfsCycles.add(dfsCycle);
                return true; // we found a cycle
            }
            if (!marked.contains(v)) {
                edgeTo.put(v, u);
                if (dfsCycle(graph, v, marked, stack, edgeTo, dfsCycles)) return true;
            }
        }
        stack.remove(u); // done processing vertex u
        return false;
    }


    private void bfs(Graph graph, String s, List<String> cycle) {
        Set<String> marked = new HashSet<>();
        Map<String, String> edgeTo = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        Queue<String> queue = new Queue<>();
        queue.enqueue(s);
        edgeTo.put(s, "-1"); // this is probably not necessary
        distance.put(s, 0);
        while (!queue.isEmpty()) {
            String u = queue.dequeue();
            for (String v : graph.adj(u)) {
                if (v.equals(s)) { // we found a cycle
                    cycle.add(v);
                    for (String x = u; !x.equals(s); x = edgeTo.get(x)) {
                        cycle.add(x);
                    }
                    cycle.add(s);
                }
                if (!marked.contains(v)) {
                    marked.add(v);
                    queue.enqueue(v);
                    distance.put(v, distance.get(u) + 1); // new distance
                    edgeTo.put(v, u);
                }
            }
        }

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
        List<String> cycle = scg.cycle(graph);
        System.out.println("== Is Cyclic ==");
        System.out.println(cycle); //[A, F, C, A]

        graph = new Graph();
        graph.addEdge("1", "2");
        graph.addEdge("2", "3");
        graph.addEdge("3", "4");
        graph.addEdge("4", "5");
        graph.addEdge("5", "1"); // This edge creates a cycle
        graph.addEdge("5", "6");
        graph.addEdge("6", "7");
        graph.addEdge("7", "8");
        graph.addEdge("8", "5");// This edge creates another cycle
        scg = new ShortestCycleDigraph();
        cycle = scg.cycle(graph);
        System.out.println("== Is Cyclic ==");
        System.out.println(cycle); // Output: [A, C, F, B, E, D]

    }
}
