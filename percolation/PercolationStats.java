/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int n; // size of grid
    private int trials; // number of trials to perform

    private double[] openSitesPerTrial;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        this.trials = trials;
        openSitesPerTrial = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                // get a random row, col between 1 and n and open
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                perc.open(row, col);
            }
            // we are sure it percolates
            openSitesPerTrial[t] = perc.numberOfOpenSites() / ((double) n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSitesPerTrial);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSitesPerTrial);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("You need 2 arguments");
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo()
                               + ", " + percolationStats.confidenceHi() + "]");

    }
}
