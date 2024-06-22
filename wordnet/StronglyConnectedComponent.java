/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 * For every pair of vertices (u,v) in the digraph there is a path from u to v
 * Kosaraju and Shari's linear time algorithm
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Graph {
    private final Map<String, List<String>> graph = new HashMap<>();

    public Graph() {

    }

    public void addEdge(String u, String v) {
        if (graph.get(u) == null) graph.put(u, new ArrayList<>());
        graph.get(u).add(v);
    }

    public int V() {
        return graph.keySet().size();
    }

    public int E() {
        int sum = 0;
        for (String u : graph.keySet()) {
            sum += graph.get(u).size();
        }
        return sum;
    }

    public Iterable<String> vertices() {
        return graph.keySet();
    }

    public List<String> adj(String u) {
        if (graph.get(u) == null) return new ArrayList<>();
        return graph.get(u);
    }

    public Graph reverse() {
        Graph reverse = new Graph();
        for (String u : graph.keySet()) {
            for (String v : graph.get(u)) {
                reverse.addEdge(v, u);
            }
        }
        return reverse;
    }
}

public class StronglyConnectedComponent {
    private Graph graph;
    private Set<String> marked;

    private int n;

    private Map<String, Integer> components;

    public StronglyConnectedComponent(Graph graph) {
        this.graph = graph;
        this.components = new HashMap<>();
        this.marked = new HashSet<>();
        // get reverse post order of G.reverse()
        Graph reverse = graph.reverse();
        List<String> postOrder = new ArrayList<>();
        for (String u : reverse.vertices()) {
            if (!marked.contains(u))
                dfs(reverse, u, marked, postOrder);
        }

        // dfs on G
        Collections.reverse(postOrder); // reverse post order
        System.out.println("postOrder = " + postOrder);
        this.marked = new HashSet<>();
        for (String u : postOrder) {
            if (!marked.contains(u)) {
                dfsCC(graph, u, marked, components);
                n++;
            }
        }
        System.out.println("components = " + components);
    }

    private void dfs(Graph graph, String u, Set<String> marked, List<String> reversePostOrder) {
        marked.add(u);
        for (String v : graph.adj(u)) {
            if (!marked.contains(v)) {
                dfs(graph, v, marked, reversePostOrder);
            }
        }
        reversePostOrder.add(u);
    }

    private void dfsCC(Graph graph, String u, Set<String> marked, Map<String, Integer> cc) {
        marked.add(u);
        cc.put(u, n);
        for (String v : graph.adj(u)) {
            if (!marked.contains(v)) {
                dfsCC(graph, v, marked, cc);
            }
        }
    }

    public int id(String u) {
        return components.get(u);
    }

    public boolean isConnected(String u, String v) {
        return id(u) == id(v);
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge("0", "1");
        graph.addEdge("0", "5");
        graph.addEdge("2", "0");
        graph.addEdge("2", "3");
        graph.addEdge("3", "2");
        graph.addEdge("3", "5");
        graph.addEdge("4", "2");
        graph.addEdge("4", "3");
        graph.addEdge("5", "4");
        graph.addEdge("6", "0");
        graph.addEdge("6", "4");
        graph.addEdge("6", "8");
        graph.addEdge("6", "9");
        graph.addEdge("7", "6");
        graph.addEdge("7", "9");
        graph.addEdge("8", "6");
        graph.addEdge("9", "10");
        graph.addEdge("9", "11");
        graph.addEdge("10", "12");
        graph.addEdge("11", "4");
        graph.addEdge("11", "12");
        graph.addEdge("12", "9");

        StronglyConnectedComponent scc = new StronglyConnectedComponent(graph);
        for (String u : graph.vertices()) {
            System.out.println(u + ": " + scc.id(u));
        }
    }
}
