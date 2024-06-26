/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordNet {

    private final Digraph graph;
    private final TreeMap<String, Integer> bst; // key: word | value: synset id

    private final Map<Integer, String> synIdToString;

    /**
     * constructor takes the name of the two input files
     * WordNet is a rooted DAG [it is acyclic and has one vertex—the root—that is an ancestor of
     * every other vertex]
     * To make sure there is one root, suffices to do a topological sort
     * every vertex must point to the root [last node in the topological sort].
     * so if we reverse the graph and do bfs or dfs starting from root, the path
     * from this traversal should contain all vertices in the graph.
     *
     * @param synsets
     * @param hypernyms
     */
    public WordNet(String synsets, String hypernyms) {
        In vertices = new In(synsets);
        bst = new TreeMap<>();
        synIdToString = new HashMap<>();
        int lineCount = 0;
        while (!vertices.isEmpty()) {
            lineCount++;
            String line = vertices.readLine();
            String[] split = line.split(",");
            for (String word : split[1].split(" ")) {
                bst.put(word, Integer.parseInt(split[0]));
            }
            synIdToString.put(Integer.parseInt(split[0]),
                              split[1]); // synset (second field of synsets.txt)
        }

        graph = new Digraph(lineCount);
        In edges = new In(hypernyms);
        while (!edges.isEmpty()) {
            String line = edges.readLine();
            String[] split = line.split(",");
            for (int i = 1; i < split.length; i++) {
                graph.addEdge(Integer.parseInt(split[0]), Integer.parseInt(split[i]));
            }
        }

        // check for no cycles
        boolean[] marked = new boolean[graph.V()];
        Set<Integer> stack = new HashSet<>();
        for (int s = 0; s < graph.V(); s++) {
            if (!marked[s]) {
                if (isCyclic(s, marked, stack))
                    throw new IllegalArgumentException("Graph has cycles");
            }
        }
        // check that there is a single root. at this point, no cycles hence we can do topological sort
        marked = new boolean[graph.V()]; // reset
        List<Integer> postOrder = new ArrayList<>();
        // run topological sort on an arbitrary vertex
        topologicalSort(10, marked, postOrder);
        System.out.println("postOrder = " + postOrder);
        int root = postOrder.get(0); // if there is one root, then this must be the root
        System.out.println("root = " + root);
        // run dfs on root
        List<Integer> dfsPathFromRoot = new ArrayList<>();
        marked = new boolean[graph.V()]; // reset
        dfs(graph.reverse(), root, marked, dfsPathFromRoot);
        System.out.println("dfsPathFromRoot = " + dfsPathFromRoot);
        if (dfsPathFromRoot.size() != graph.V())
            throw new IllegalArgumentException("Graph is not rooted");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return bst.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (bst.containsKey(word)) return true;
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        int synIdA = bst.get(nounA);
        int synIdB = bst.get(nounB);
        int lcId = bfsCommonAncestor(synIdA, synIdB);

        return bfsDistance(synIdA, lcId) + bfsDistance(synIdB, lcId);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int synIdA = bst.get(nounA);
        int synIdB = bst.get(nounB);
        int lcId = bfsCommonAncestor(synIdA, synIdB);

        return synIdToString.get(lcId);
    }

    // find shortest common ancestor using bfs
    private int bfsCommonAncestor(int idA, int idB) {
        boolean[] marked = new boolean[graph.V()];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(idA);
        queue.enqueue(idB);
        marked[idA] = true;
        marked[idB] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    queue.enqueue(w);
                }
                else {
                    // common ancestor found
                    return w;
                }
            }
        }
        throw new IllegalArgumentException("something wrong with inputs to bfs");
    }

    private int bfsDistance(int from, int to) {
        boolean[] marked = new boolean[graph.V()];
        int[] dist = new int[graph.V()];
        int[] edgeTo = new int[graph.V()];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(from);
        dist[from] = 0;
        edgeTo[from] = -1;
        marked[from] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    queue.enqueue(w);
                    dist[w] = dist[v] + 1;
                    edgeTo[w] = v;
                    if (w == to) return dist[w];
                }
            }
        }
        throw new IllegalArgumentException("something wrong with inputs to bfs");
    }

    private boolean isCyclic(int v, boolean[] marked, Set<Integer> stack) {
        marked[v] = true;
        stack.add(v);
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                if (isCyclic(w, marked, stack)) {
                    return true;
                }
            }
            else if (stack.contains(w)) { // found a cycle
                return true;
            }
        }
        stack.remove(v);
        return false;
    }

    private void topologicalSort(int v, boolean[] marked, List<Integer> postOrder) {
        marked[v] = true;
        // visit all children first
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                topologicalSort(w, marked, postOrder);
            }
        }
        postOrder.add(v); // process node last
    }

    private void dfs(Digraph g, int v, boolean[] marked, List<Integer> path) {
        marked[v] = true;
        path.add(v);
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w, marked, path);
            }
        }
    }

    // some unit testing
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets12.txt", "hypernyms12.txt");
        String sap = wordNet.sap("d", "k");
        System.out.println("sap = " + sap);
        int distance = wordNet.distance("d", "k");
        System.out.println("distance = " + distance);

    }
}
