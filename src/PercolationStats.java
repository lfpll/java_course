import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] percentOpen;
    private int trialsNumber;
    // Statistics gathered from the process
    private double mean;
    private double stdev;
    private double low;
    private double high;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        trialsNumber = trials;
        percentOpen = new double[trials];
        // Run Sample code with number of tries
        for (int experiment = 0; experiment <= trials -1; experiment++) {
            runTrie(n,experiment);
        }
    }

    private void runTrie(int n, int experiment_num)
    {
        Percolation percTest = new Percolation(n);
        int[] random = StdRandom.permutation(n * n);
        // Running one sample
        for (int index = 0; index < n * n; index++) { ;
            int randIndex = random[index];
            int row = 1 + randIndex / n;
            int col = 1 + randIndex % n;
            percTest.open(row, col);
            if (percTest.percolates()) {
                break;
            }
        }
        // Adding the percentage of number of sites to this experiment
        double result = percTest.numberOfOpenSites();
        percentOpen[experiment_num] = result/(n*n);
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(percentOpen);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stdev = StdStats.stddev(percentOpen);
        return stdev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - Math.sqrt(1.96*1.96*stdev/trialsNumber);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + Math.sqrt(1.96*1.96*stdev/trialsNumber);
    }

    // test client (see below)
    public static void main(String[] args) {
        int trials = Integer.parseInt(args[1]);
        int sampleSize = Integer.parseInt(args[0]);
        PercolationStats statsSample = new PercolationStats(sampleSize, trials);
        double mean = statsSample.mean();
        double stdev = statsSample.stddev();
        double loConf = statsSample.confidenceLo();
        double hiConf = statsSample.confidenceHi();
        System.out.println(mean);
        System.out.println(stdev);
        System.out.println(loConf);
        System.out.println(hiConf);
    }

}