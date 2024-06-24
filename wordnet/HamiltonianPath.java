/* *****************************************************************************
 *  Name: Amin Tazifor
 *  Date:
 *  Description: Given a directed acyclic graph, design a linear-time algorithm to determine whether it has a Hamiltonian path
 *  (a simple path that visits every vertex)
 *  DAG implies there is a topological sort
 *  So do a topological sort and iterate through, if (u, v) is the topological order, then there should be an edge u->v
 **************************************************************************** */


import edu.princeton.cs.algs4.Stack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HamiltonianPath {

    public HamiltonianPath() {

    }

    public boolean existsHamiltonianPath(Graph graph) {
        Iterable<String> topologicalOrder = topologicalOrder(graph);
        String prev = null;
        for (Iterator<String> it = topologicalOrder.iterator(); it.hasNext(); ) {
            String current = it.next();
            if (prev == null) prev = current;
            else {
                if (!graph.adj(prev).contains(current)) return false;
                prev = current;
            }
        }
        return true;
    }

    private Iterable<String> topologicalOrder(Graph graph) {
        Set<String> marked = new HashSet<>();
        Stack<String> reversePostOrder = new Stack<>();
        for (String s : graph.vertices()) {
            if (!marked.contains(s))
                dfs(graph, s, marked, reversePostOrder);
        }
        return reversePostOrder;
    }

    private void dfs(Graph graph, String u, Set<String> marked, Stack<String> reversePostOrder) {
        marked.add(u);
        for (String v : graph.adj(u)) {
            if (!marked.contains(v)) {
                dfs(graph, u, marked, reversePostOrder);
            }
        }
        reversePostOrder.push(u); // post order
    }

    public static void main(String[] args) {

    }
}
