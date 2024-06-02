/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private int blankRow;
    private int blankCol;

    public Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) { // defensive copy
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
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

    private static int[][] goal(int n) {
        int[][] goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goal[i][j] = i * n + j + 1;
            }
        }
        goal[n - 1][n - 1] = 0;
        return goal;
    }

    // number of tiles out of place
    public int hamming() {
        int n = dimension();
        int[][] goal = goal(n);
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
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!(i == (n - 1) && j == (n - 1)))
                    if (tiles[i][j] != i * n + j + 1) return false;
            }
        }
        if (tiles[n - 1][n - 1] != 0) return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (this.dimension() != other.dimension()) {
            return false;
        }
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int n = dimension();
        List<Board> neigbhors = new ArrayList<>();
        if (blankCol - 1 >= 0) neigbhors.add(neighbor(blankRow, blankCol - 1));
        if (blankRow - 1 >= 0) neigbhors.add(neighbor(blankRow - 1, blankCol));
        if (blankCol + 1 < n) neigbhors.add(neighbor(blankRow, blankCol + 1));
        if (blankRow + 1 < n) neigbhors.add(neighbor(blankRow + 1, blankCol));

        return neigbhors;
    }

    private Board neighbor(int row, int col) {
        int swap = tiles[blankRow][blankCol];

        int n = dimension();
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
                if (i == blankRow && j == blankCol) copy[i][j] = tiles[row][col];
                if (i == row && j == col) copy[i][j] = swap;
            }
        }
        return new Board(copy);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int n = dimension();
        int[][] copy = copy(tiles);
        int r1 = -1;
        int r2 = -1;
        int c1 = -1;
        int c2 = -1;
        // swap tiles that are adjacent to each other on the same row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (copy[i][j] != 0 && copy[i][j + 1] != 0) {
                    exch(copy, i, j, i, j + 1);
                    return new Board(copy);
                }
            }
        }
        // in case no adjacent non-zero tiles in rows, check in columns
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n; j++) {
                if (copy[i][j] != 0 && copy[i + 1][j] != 0) {
                    exch(copy, i, j, i + 1, j);
                    return new Board(copy);
                }
            }
        }
        throw new RuntimeException("No valid twin found");
    }

    // swap two tiles (r1,c1) and (r2, c2)
    private void exch(int[][] a, int r1, int c1, int r2, int c2) {
        int swap = a[r1][c1];
        a[r1][c1] = a[r2][c2];
        a[r2][c2] = swap;
    }

    private int[][] copy(int[][] a) {
        int n = a.length;
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = a[i][j];
            }
        }
        return b;
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

        int[][] tiles3 = {
                { 1, 2, 3 },
                { 4, 5, 8 },
                { 7, 6, 0 }
        };
        board = new Board(tiles3);
        System.out.println(board.toString());
        System.out.println("tiles3 is goal? " + board.isGoal());

        int[][] tiles4 = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };
        board = new Board(tiles4);
        System.out.println(board.toString());
        System.out.println("tiles4 is goal? " + board.isGoal());

        int[][] tiles5 = {
                { 1, 3 },
                { 2, 0 }
        };
        board = new Board(tiles5);
        System.out.println(board.toString());
        System.out.println("tiles5 is goal? " + board.isGoal());
        System.out.println("tiles5 twin " + board.twin());

        int[][] tiles6 = {
                { 3, 0 },
                { 1, 2 }
        };
        int[][] tiles7 = {
                { 3, 2 },
                { 1, 0 }
        };
        board = new Board(tiles6);
        System.out.println(board.toString());
        System.out.println("tiles6 equal? " + board.equals(new Board(tiles6)));
        Board board7 = new Board(tiles7);
        List<Board> visited = new ArrayList<>();
        visited.add(board7);
        for (Board b : board.neighbors()) {
            System.out.print("board7 = " + board7);
            System.out.print("\t");
            System.out.println("neighor = " + b);
            System.out.println("b.equals(tiles7) = " + b.equals(board7));
            if (!visited.contains(b))
                visited.add(b);
        }
        System.out.println("visited.size() = " + visited.size());
    }
}
