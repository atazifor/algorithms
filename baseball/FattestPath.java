/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.List;

public class FattestPath {
    private FlowEdge[] edgeTo;
    private boolean[] marked;
    private double flow;

    public FattestPath(FlowNetwork G, int s, int t) {
        double threshold = fattestEdge(G);
        while (threshold >= 1) {
            while (hasAugmentingPath(G, s, t, threshold)) {
                double bottleneck = Double.MAX_VALUE;
                for (int v = t; v != s; v = edgeTo[v].other(v)) {
                    bottleneck = Math.min(bottleneck, edgeTo[v].residualCapacityTo(v));
                }

                for (int v = t; v != s; v = edgeTo[v].other(v)) {
                    edgeTo[v].addResidualFlowTo(v, bottleneck);
                }
                flow += bottleneck;
            }
            threshold = threshold / 2;
        }
    }

    public double maxFlow() {
        return flow;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t, double threshold) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (!marked[w] && e.residualCapacityTo(w) > threshold) {
                    marked[w] = true;
                    edgeTo[w] = e;
                    q.enqueue(w);
                }
            }
        }
        return marked[t]; // we found a path to t with a residual capacity as big as the threshold
    }

    private double fattestEdge(FlowNetwork G) {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < G.V(); i++) {
            for (FlowEdge e : G.adj(i)) {
                max = Math.max(max, e.capacity());
            }
        }
        return max;
    }

    public static void main(String[] args) {
        // Number of vertices including source (s) and sink (t)
        int n = 8; // 0 for s, 1 to 6 for other vertices, and 7 for t

        // Create the flow network
        FlowNetwork flowNetwork = new FlowNetwork(n);

        // Add edges
        flowNetwork.addEdge(new FlowEdge(0, 1, 10));
        flowNetwork.addEdge(new FlowEdge(0, 2, 5));
        flowNetwork.addEdge(new FlowEdge(0, 3, 15));
        flowNetwork.addEdge(new FlowEdge(1, 4, 9));
        flowNetwork.addEdge(new FlowEdge(1, 5, 15));
        flowNetwork.addEdge(new FlowEdge(1, 2, 4));
        flowNetwork.addEdge(new FlowEdge(2, 5, 8));
        flowNetwork.addEdge(new FlowEdge(2, 3, 4));
        flowNetwork.addEdge(new FlowEdge(3, 6, 16));
        flowNetwork.addEdge(new FlowEdge(4, 6, 15));
        flowNetwork.addEdge(new FlowEdge(4, 7, 10));
        flowNetwork.addEdge(new FlowEdge(5, 6, 15));
        flowNetwork.addEdge(new FlowEdge(5, 7, 10));
        flowNetwork.addEdge(new FlowEdge(6, 2, 6));
        flowNetwork.addEdge(new FlowEdge(6, 7, 10));

        // Compute max flow from s (0) to t (8)
        FattestPath maxFlow = new FattestPath(flowNetwork, 0, 7);
        System.out.println("Max flow value: " + maxFlow.maxFlow());
        List<Integer> minCut = new ArrayList<>();
        for (int i = 0; i < flowNetwork.V(); i++) {
            if (maxFlow.inCut(i)) minCut.add(i);
        }
        System.out.println("minCut = " + minCut);
    }
}
