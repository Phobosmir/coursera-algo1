import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private int openSitesCounter;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final int topSiteIndex;
    private final int bottomSiteIndex;


    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException("Number of elements should be a positive number");

        this.n = n;
        this.topSiteIndex = n * n;
        this.bottomSiteIndex = n * n + 1;


        this.openSitesCounter = 0;
        openSites = new boolean[n * n];
        for (int i = 0; i < n * n; i++)
            openSites[i] = false;


        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            uf.union(topSiteIndex, i);
            uf.union(bottomSiteIndex, i + n * (n - 1));
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validateArgs(row, col);


        int elementIndex = getIndex(row, col);
        if (openSites[elementIndex]) {
            return;
        }

        openSitesCounter++;
        openSites[elementIndex] = true;


        if (row > 1) {
            int neightborUpIndex = getIndex(row - 1, col);
            if (openSites[neightborUpIndex]) {
                uf.union(elementIndex, neightborUpIndex);
            }
        }

        if (col > 1) {
            int neightborLeft = getIndex(row, col - 1);
            if (openSites[neightborLeft]) {
                uf.union(elementIndex, neightborLeft);
            }
        }

        if (row < n) {
            int neightborDown = getIndex(row + 1, col);
            if (openSites[neightborDown]) {
                uf.union(elementIndex, neightborDown);
            }
        }

        if (col < n) {
            int neightborRight = getIndex(row, col + 1);
            if (openSites[neightborRight]) {
                uf.union(elementIndex, neightborRight);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateArgs(row, col);

        return openSites[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateArgs(row, col);
        if (!isOpen(row, col))
            return false;

        if (uf.find(getIndex(row, col)) == uf.find(topSiteIndex))
            return true;

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topSiteIndex) == uf.find(bottomSiteIndex);
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: [dimension]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        Percolation p = new Percolation(n);
        printContent(p, n);

        StdOut.println("Randmly opening sites...");
        for (int i = 0; i < 17; i++) {
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);

            p.open(row + 1, col + 1);
        }

        StdOut.println("Result:");
        printContent(p, n);
        if (p.percolates())
            StdOut.println("Percolates");
    }

    private void validateArgs(int rowIndex, int colIndex) {
        if (rowIndex <= 0 || rowIndex > n)
            throw new IllegalArgumentException(
                    "row index (" + rowIndex + ") should be between 1 and " + n);
        if (colIndex <= 0 || colIndex > n)
            throw new IllegalArgumentException(
                    "row index (" + colIndex + ") should be between 1 and " + n);
    }

    private static void printContent(Percolation p, int n) {
        for (int rowIndex = 1; rowIndex <= n; rowIndex++) {
            for (int colIndex = 1; colIndex <= n; colIndex++) {
                if (p.isOpen(rowIndex, colIndex)) {
                    if (p.isFull(rowIndex, colIndex))
                        StdOut.print(String.format("%d ", 2));
                    else
                        StdOut.print(String.format("%d ", 1));
                }
                else
                    StdOut.print(String.format("%d ", 0));
            }
            StdOut.println();
        }
    }


}
