/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;

    public Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) { // defensive copy
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.length)
          .append("\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                sb.append(" " + tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int n = dimension();
        int[][] goal = goal();
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue; // black square
                if (tiles[i][j] != goal[i][j]) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int n = dimension();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue; // black square
                int row = (tiles[i][j] - 1) / n;
                int col = (tiles[i][j] - 1) % n;
                sum += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(this.goal());
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        Board other = (Board) y;
        if (this.dimension() != other.dimension()) return false;
        return this.toString().equals(other.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row = -1;
        int col = -1;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1 || col != -1) break;
        }
        List<Board> neigbhors = new ArrayList<>();
        if (col - 1 >= 0) neigbhors.add(neighbor(row, col, row, col - 1));
        if (row - 1 >= 0) neigbhors.add(neighbor(row, col, row - 1, col));
        if (col + 1 < n) neigbhors.add(neighbor(row, col, row, col + 1));
        if (row + 1 < n) neigbhors.add(neighbor(row, col, row + 1, col));

        return neigbhors;
    }

    private Board neighbor(int row, int col, int i, int j) {
        int swap = tiles[row][col];
        tiles[row][col] = tiles[i][j];
        tiles[i][j] = swap;
        Board neighbor = new Board(tiles); // defensive copy of tiles will be created
        // go back to original state of tiles. Will not affect copy in neighbor
        swap = tiles[row][col];
        tiles[row][col] = tiles[i][j];
        tiles[i][j] = swap;
        return neighbor;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return this.neighbors().iterator().next();
    }

    private int[][] goal() {
        int n = dimension();
        int[][] goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goal[i][j] = i * n + j + 1;
            }
        }
        goal[n - 1][n - 1] = 0;

        return goal;
    }

    public static void main(String[] args) {
        int[][] tiles = {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };
        Board board = new Board(tiles);
        System.out.println(board.toString());
        System.out.println("is goal? " + board.isGoal());
        int[][] tiles2 = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        Board board2 = new Board(tiles2);
        System.out.println(board2);
        System.out.println("hamming() = " + board2.hamming());
        System.out.println("manhattan() = " + board2.manhattan());
        System.out.println(" === neighbors of ===");
        System.out.println(board);
        System.out.println(" ===   ===");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
    }
}
