/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;

public class MaxWeightClosure {

    public static FlowNetwork createFlowNetwork(Digraph G, double[] weights) {
        int V = G.V();
        int s = V;
        int t = V + 1;
        FlowNetwork flowNetwork = new FlowNetwork(V + 2);
        // for positive wei
        for (int v = 0; v < V; v++) {
            if (weights[v] > 0) { // positive weights increase
                flowNetwork.addEdge(new FlowEdge(s, v, weights[v]));
            }
            else if (weights[v] < 0) {
                flowNetwork.addEdge(new FlowEdge(v, t, -1 * weights[v]));
            }
        }
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                flowNetwork.addEdge(new FlowEdge(v, w, Double.MAX_VALUE));
            }
        }
        return flowNetwork;
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        FlowNetwork flowNetwork = createFlowNetwork(G, new double[] { 3, -2, 4 });
        FordFulkerson ff = new FordFulkerson(flowNetwork, G.V(), G.V() + 1);
        System.out.println("ff.maxFlow() = " + ff.maxFlow());
        List<Integer> minCut = new ArrayList<>();
        for (int i = 0; i < G.V(); i++) {
            if (ff.inCut(i))
                minCut.add(i);
        }
        System.out.println("minCut = " + minCut);
    }
}
