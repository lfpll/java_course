import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int matrixLength;
    private WeightedQuickUnionUF unionTree;
    private Boolean[][] openTree;
    private int numberOfOpens;
    private int virtualTop;
    private int virtualBot;

    private void populateArray(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                openTree[i][j] = false;
            }
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid value for n");
        }
        virtualTop = 0;
        virtualBot = n * n + 1;
        unionTree = new WeightedQuickUnionUF(n * n + 2);
        openTree = new Boolean[n][n];
        matrixLength = n;
        populateArray(n);
    }

    // Checks to see if the size is possible to be valid
    private void checkSize(int rowOrColumnSize) {
        if (rowOrColumnSize > matrixLength) {
            throw new IllegalArgumentException("Size of the function invalid");
        }
    }

    // Connect dots if they are open
    private void connectIfOpen(int row, int col, int rowChild, int colChild) {
        if (openTree[rowChild-1][colChild-1]) {
            int fatherIndex = getIndex(row, col);
            int childIndex = getIndex(rowChild, colChild);
            unionTree.union(fatherIndex, childIndex);
        }
    }

    private int getIndex(int row, int col) {
        // Adding one because of the zero virtual bottom
        return (row-1) * matrixLength + col + 1;
    }

    // Connecting the sides of the indexes
    private void connectSides(int row, int col) {
        // Connect top and bottom if exists
        if (row != matrixLength) {
            connectIfOpen(row, col, row + 1, col);
        }
        if (row != 1) {
            connectIfOpen(row, col, row - 1, col);
        }
        // Connect to left and right if exists
        if (col != 1) {
            connectIfOpen(row, col, row, col - 1);
        }
        if (col != matrixLength) {
            connectIfOpen(row, col, row, col + 1);
        }
        if (row == matrixLength){
            unionTree.union(getIndex(row,col),virtualTop);
        }
        if (row == 1){
            unionTree.union(getIndex(row,col),virtualBot);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkSize(row);
        checkSize(col);
        if (!openTree[row-1][col-1]) {
            numberOfOpens++;
            openTree[row-1][col-1] = true;
        }
        connectSides(row, col);
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkSize(row);
        checkSize(col);
        // Check if the first index is bigger then matrixLength meaning it's connect to the first row
        int fieldIndex = getIndex(row, col);
        return unionTree.connected(fieldIndex,virtualBot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpens;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionTree.connected(virtualTop,virtualBot);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation testPer = new Percolation(4);
        testPer.percolates();
        testPer.open(2, 2);
        testPer.open(2, 2);
        testPer.open(2, 3);
        testPer.percolates();
        testPer.open(3, 1);
        testPer.percolates();

    }
}
