/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class FordFulkerson {

    private FlowEdge[] edgeTo;
    private boolean[] marked;
    private double flow;

    public FordFulkerson(FlowNetwork G, int s, int t) {
        while (hasAugmentingPath(G, s, t)) {
            // find bottleneck
            double bottleneck = Double.MAX_VALUE;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottleneck = Math.min(bottleneck, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow with bottleneck
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottleneck);
            }
            flow += bottleneck;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = e;
                    queue.enqueue(w);
                }
            }
        }
        return marked[t];
    }

    public double maxFlow() {
        return flow;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    public static void main(String[] args) {

    }
}
