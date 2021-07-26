/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double confLevelConstant;
    private final int[] percolationResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Args should be positive");

        this.confLevelConstant = 1.96;
        this.trials = trials;

        percolationResults = new int[trials];

        for (int curRun = 0; curRun < trials; curRun++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                StdRandom.setSeed(curRun);
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            percolationResults[curRun] = p.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confLevelConstant * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confLevelConstant * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2)
            return;

        int number = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(number, trials);
        StdOut.println("Percolation results: ");
        StdOut.println(String.format("Area: %d x %d", number, number));
        StdOut.println(String.format("Trials: %d", trials));
        StdOut.println(String.format("Mean: %f", ps.mean()));
        StdOut.println(String.format("Stddev: %f", ps.stddev()));
        StdOut.println(String.format("Low conf (95%%): %f", ps.confidenceLo()));
        StdOut.println(String.format("High conf (95%%): %f", ps.confidenceHi()));

    }
}
