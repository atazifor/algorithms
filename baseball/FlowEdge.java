/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class FlowEdge {
    private final int v;
    private final int w;
    private final double capacity;
    private double flow;

    public FlowEdge(int v, int w, double capaciy) {
        this.v = v;
        this.w = w;
        this.capacity = capaciy;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        throw new IllegalArgumentException("invalid vertex");
    }

    private double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v)
            return flow; // backward edge
        else if (vertex == w)
            return capacity - flow; // forward edge
        throw new IllegalArgumentException("Not a valid vertex");
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v) flow -= delta; // backward edge
        else if (vertex == w) flow += delta; // forward edge
        throw new IllegalArgumentException("Not a valid vertext");//
    }


}
