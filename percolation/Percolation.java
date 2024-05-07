/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;

    private int openSiteCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        // blocked = 0, open = 1
        grid = new boolean[n][n];
        // create two virtual open sites;
        uf = new WeightedQuickUnionUF(n * n + 2);

        // virtual top is connected to every top site
        for (int i = 1; i <= n; i++)
            uf.union(0, i);
        // virtual bottom is connected to every bottom site
        for (int i = n * n; i > n * n - n; i--)
            uf.union(n * n + 1, i);
    }

    // opens the site (row, col) if it is not open already
    // row, col in grid corresponds to (N*row + col) + 1 in union find data structure
    public void open(int row, int col) {
        int n = grid.length;
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        if (!isOpen(row, col)) {
            // open the site
            grid[row - 1][col - 1] = true;
            // increase count of open sites
            openSiteCount++;

            int p = to1D(n, row, col) + 1; // grid row, col to flattened array index for union-find

            // neighboring up cell
            int r = row - 1;
            if (r >= 1 && isOpen(r, col))
                uf.union(p, to1D(n, r, col) + 1);

            // neighboring down cell
            r = row + 1;
            if (r <= n && isOpen(r, col))
                uf.union(p, to1D(n, r, col) + 1);

            // neighboring left cell
            int c = col - 1;
            if (c >= 1 && isOpen(row, c))
                uf.union(p, to1D(n, row, c) + 1);

            // neighboring right cell
            c = col + 1;
            if (c <= n && isOpen(row, c))
                uf.union(p, to1D(n, row, c) + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int n = grid.length;
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();
        return grid[row - 1][col - 1] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int n = grid.length;
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();
        // int q = n * (row - 1) + (col - 1) + 1;
        int q = to1D(n, row, col) + 1;
        // open and virtual top is connected to object
        return isOpen(row, col) && uf.find(0) == uf.find(q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = grid.length;
        return numberOfOpenSites() > 0 && uf.find(0) == uf.find(n * n + 1);
    }

    private int to1D(int n, int row, int col) {
        return n * (row - 1) + (col - 1);
    }

    public static void main(String[] args) {

    }
}
