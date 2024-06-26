/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordNet {

    // private final Digraph graph;
    private final TreeMap<String, Integer> bst; // key: word | value: synset id

    private final Map<Integer, String> synIdToString;

    private final SAP sap;

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
        // line count
        // System.out.println("lineCount = " + lineCount);
        Digraph graph = new Digraph(lineCount);
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
                if (isCyclic(graph, s, marked, stack))
                    throw new IllegalArgumentException("Graph has cycles");
            }
        }
        // check that there is a single root. at this point, no cycles hence we can do topological sort
        marked = new boolean[graph.V()]; // reset
        List<Integer> postOrder = new ArrayList<>();
        // run topological sort on an arbitrary vertex
        topologicalSort(graph, 0, marked, postOrder);
        // System.out.println("postOrder = " + postOrder);
        int root = postOrder.get(0); // if there is one root, then this must be the root
        // System.out.println("root = " + root);
        // run dfs on root
        List<Integer> dfsPathFromRoot = new ArrayList<>();
        marked = new boolean[graph.V()]; // reset
        dfs(graph.reverse(), root, marked, dfsPathFromRoot);
        // System.out.println("dfsPathFromRoot = " + dfsPathFromRoot);
        if (dfsPathFromRoot.size() != graph.V())
            throw new IllegalArgumentException("Graph is not rooted");

        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return bst.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("can't have a null argument");
        if (bst.containsKey(word)) return true;
        return false;
    }

    /**
     * distance between nounA and nounB
     * Get a list of all synsets in which nounA appears [listA]
     * Get a list of all synsets in which nounB appears [listB]
     * then distance(nounA, nounB) = sap.distance(listA, listB)
     *
     * @param nounA
     * @param nounB
     * @return
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("can't have null arguments");
        if (nounA.equals(nounB)) return 0;
        Set<Integer> synsetIdA = new HashSet<>();
        Set<Integer> synsetIdB = new HashSet<>();
        for (Map.Entry<Integer, String> s : synIdToString.entrySet()) {
            Integer id = s.getKey();
            String synset = s.getValue();
            if (synset.contains(nounA)) synsetIdA.add(id);
            else if (synset.contains(nounB)) synsetIdB.add(id);
        }
        int length = sap.length(synsetIdA, synsetIdB);
        if (length == -1)
            throw new IllegalArgumentException("one of the nouns is not in the lexicon");
        return length;
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("can't have null arguments");
        if (nounA.equals(nounB)) return nounA;
        Set<Integer> synsetIdA = new HashSet<>();
        Set<Integer> synsetIdB = new HashSet<>();
        for (Map.Entry<Integer, String> s : synIdToString.entrySet()) {
            Integer id = s.getKey();
            String synset = s.getValue();
            if (synset.contains(nounA)) synsetIdA.add(id);
            if (synset.contains(nounB)) synsetIdB.add(id);
        }
        int ancestor = sap.ancestor(synsetIdA, synsetIdB);
        if (ancestor == -1)
            throw new IllegalArgumentException("one of the nouns is not in the lexicon");
        return synIdToString.get(ancestor);
    }

    private boolean isCyclic(Digraph graph, int v, boolean[] marked, Set<Integer> stack) {
        marked[v] = true;
        stack.add(v);
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                if (isCyclic(graph, w, marked, stack)) {
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

    private void topologicalSort(Digraph graph, int v, boolean[] marked, List<Integer> postOrder) {
        marked[v] = true;
        // visit all children first
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                topologicalSort(graph, w, marked, postOrder);
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
        String nounA = "d";
        String nounB = "k";
        String sap = wordNet.sap(nounA, nounB);
        int distance = wordNet.distance(nounA, nounB);
        StdOut.printf("[%s, %s] | sap = %s | distance = %d\n", nounA, nounB, sap, distance);

        nounA = "b";
        nounB = "c";
        sap = wordNet.sap(nounA, nounB);
        distance = wordNet.distance(nounA, nounB);
        StdOut.printf("[%s, %s] | sap = %s | distance = %d\n", nounA, nounB, sap, distance);

        nounA = "g";
        nounB = "g";
        sap = wordNet.sap(nounA, nounB);
        distance = wordNet.distance(nounA, nounB);
        StdOut.printf("[%s, %s] | sap = %s | distance = %d\n", nounA, nounB, sap, distance);

        nounA = "a";
        nounB = "b";
        sap = wordNet.sap(nounA, nounB);
        distance = wordNet.distance(nounA, nounB);
        StdOut.printf("[%s, %s] | sap = %s | distance = %d\n", nounA, nounB, sap, distance);
    }
}
