/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    // private MinPQ<Node> minPQ;
    private List<Board> path;
    private int numberOfMoves;
    private final Board initial;
    private boolean solvable;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Constructor can't be null");
        this.initial = initial;
        // this.minPQ = new MinPQ<>();
        this.solvable = false;
        this.path = null;
        this.numberOfMoves = -1;

        solve();
    }

    private void solve() {
        if (initial.isGoal()) {
            path = new ArrayList<>();
            path.add(initial);
            solvable = true;
            numberOfMoves = 0;
            return;
        }

        MinPQ<Node> initialQ = new MinPQ<>();
        initialQ.insert(new Node(initial, 0, null));
        MinPQ<Node> twinQ = new MinPQ<>();
        twinQ.insert(new Node(initial.twin(), 0, null));
        Node solutionNode = null;
        while (true) {
            solutionNode = step(initialQ);
            // guaranteed we must get a solution from either the initial board [solutionNode != null]
            // OR
            // its twin [step(twinQ) != null]
            if (solutionNode != null || step(twinQ) != null) break;
        }

        if (solutionNode != null) { // we got the solution from the initial board
            path = reconstructPath(solutionNode);
            solvable = true;
            numberOfMoves = path.size() - 1;
        }
        else {
            path = null;
            solvable = false;
            numberOfMoves = -1;
        }
    }

    private Node step(MinPQ<Node> minPQ) {
        Node node = minPQ.delMin();
        if (node.board.isGoal()) return node;
        Iterable<Board> neighbors = node.board.neighbors();
        for (Board neighbor : neighbors) {
            if (node.previous != null && node.previous.board.equals(neighbor)) continue;
            minPQ.insert(new Node(neighbor, node.moves + 1, node));
        }
        return null;
    }

    private List<Board> reconstructPath(Node node) {
        List<Board> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.board);
            node = node.previous;
        }
        return path;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numberOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable() ? path : null;
    }

    private class Node implements Comparable<Node> {
        Board board;
        int moves;
        Node previous;

        private int manhathan;

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhathan = board.manhattan();
        }

        public int compareTo(Node other) {
            return Integer.compare(this.manhathan + this.moves,
                                   other.manhathan + other.moves);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        /*Board initial = new Board(new int[][] {
                { 3, 2 },
                { 1, 0 }
        });*/

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
