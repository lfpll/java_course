import edu.princeton.cs.algs4.QuickFindUF;

import java.lang.reflect.Array;


public class Percolation {
    //    int[][] matrix;
    private int matrixLength;
    private QuickFindUF unionTree;
    private Boolean[][] openTree;

    private void populateArray(int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                openTree[i][j] = false;
            }
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid value for n");
        }
        unionTree = new QuickFindUF(n * n);
        openTree = new Boolean[n][n];
        matrixLength = n - 1;
        populateArray(n-1);
    }

    // Checks to see if the size is possible to be valid
    private void checkSize(int maxLength) {
        if (maxLength > matrixLength) {
            throw new IllegalArgumentException("Size of the function invalid");
        }
    }

    // Connect dots if they are open
    private void connectIfOpen(int row, int col, int rowChild, int colChild) {
        if (isOpen(rowChild, colChild)) {
            int fatherIndex = getIndex(row, col);
            int childIndex = getIndex(rowChild, colChild);
            // Making the smallest one be the dominant index
            if (fatherIndex > childIndex) {
                unionTree.union(fatherIndex, childIndex);
            } else {
                unionTree.union(childIndex, fatherIndex);
            }
        }
    }

    private int getIndex(int row, int col) {
        return (row * matrixLength + col);
    }

    // Connecting the sides of the indexes
    private void connectSides(int row, int col) {
        // Connect top and bottom if exists
        if (row != matrixLength) {
            connectIfOpen(row, col, row + 1, col);
        }
        if (row != 0) {
            connectIfOpen(row, col, row - 1, col);
        }
        // Connect to left and right if exists
        if (col != 0) {
            connectIfOpen(row, col, row, col - 1);
        }
        if (col != matrixLength) {
            connectIfOpen(row, col, row, col + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkSize(row);
        checkSize(col);
        if (!openTree[row][col]) {
            openTree[row][col] = true;
        }
        connectSides(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkSize(row);
        checkSize(col);
        return openTree[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkSize(row);
        checkSize(col);
        // Check if the first index is bigger then matrixLength meaning it's connect to the first row
        return unionTree.find(getIndex(row, col)) <= matrixLength;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return unionTree.count();
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i <= matrixLength; i++) {
            // Check by column if the index is
            if (unionTree.find(getIndex(matrixLength, i)) <= matrixLength) {
                System.out.print("perlocates");
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation testPer = new Percolation(4);
        testPer.percolates();
        testPer.open(0,1);
        testPer.open(1,1);
        testPer.open(2,1);
        testPer.percolates();
        testPer.open(3,1);
        testPer.percolates();

    }
}
