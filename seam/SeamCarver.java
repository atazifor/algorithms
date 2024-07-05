/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SeamCarver {
    private final Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        double[][] disTo = new double[width()][height()];
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                disTo[col][row] = Double.POSITIVE_INFINITY;
            }
            disTo[col][0] = 0; // starting from top row of every column
        }

        double minDistance = Double.POSITIVE_INFINITY;
        int[] minPath = null;

        for (int col = 0; col < width(); col++) {
            int[] edgeTo = new int[height()]; // index is row, entry is column
            edgeTo[0] = col;
            Queue<int[]> queue = new Queue<>();
            queue.enqueue(new int[] { col, 0 });
            while (!queue.isEmpty()) {
                int[] v = queue.dequeue();
                for (int[] w : adj(v)) {
                    queue.enqueue(w);
                    // System.out.println(String.format("w:[%s, %s]", w[1], w[0]));
                    relaxVertical(v, w, disTo, edgeTo);
                    // System.out.println(String.format("END w:[%s, %s]", w[1], w[0]));
                }
            }
            StdOut.println("disFrom " + col);
            int width = width();
            int height = height();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    StdOut.printf(" %.2f", disTo[j][i]);
                }
                StdOut.println();
            }
            double verticalDistance = verticalDistance(edgeTo, disTo);
            if (verticalDistance < minDistance) {
                minDistance = verticalDistance;
                minPath = edgeTo;
            }
        }
        return minPath;
    }

    private double verticalDistance(int[] vertical, double[][] disTo) {
        double sum = 0;
        for (int row = 0; row < vertical.length; row++) {
            int col = vertical[row];
            sum += disTo[col][row];
        }
        return sum;
    }

    private void relaxVertical(int[] v, int[] w, double[][] disTo, int[] edgeTo) {
        int col = w[0];
        int row = w[1];
        double e2 = energy(col, row);
        if (disTo[col][row] > e2) {
            disTo[col][row] = e2;
            edgeTo[row] = col;
            System.out.println(String.format("edgeTo[%s]:%s", row, col));
        }
    }

    private Iterable<int[]> adj(int[] v) {
        List<int[]> adj = new ArrayList<>();
        int col = v[0];
        int row = v[1];
        if (col - 1 >= 0 && row + 1 <= height() - 1) adj.add(new int[] { col - 1, row + 1 });
        if (row + 1 <= height() - 1) adj.add(new int[] { col, row + 1 });
        if (col + 1 <= width() - 1 && row + 1 <= height() - 1)
            adj.add(new int[] { col + 1, row + 1 });
        System.out.println("adj.size() = " + adj.size());
        return adj;
    }

    public int height() {
        return picture().height();
    }

    public int width() {
        return picture().width();
    }

    public double energy(int col, int row) {
        if (col < 0 || col > width() - 1 || row < 0 || row > height() - 1)
            throw new IllegalArgumentException("coordinates are out of bounds");

        // energy of a pixel at the border of the image to be 1000
        if (col == 0 || row == 0 || col == width() - 1
                || row == height() - 1)
            return 1000.00;
        Color colorXA = picture().get(col + 1, row);
        Color colorXB = picture().get(col - 1, row);
        double xGradientSq = Math.pow(colorXA.getRed() - colorXB.getRed(), 2) +
                Math.pow(colorXA.getGreen() - colorXB.getGreen(), 2) +
                Math.pow(colorXA.getBlue() - colorXB.getBlue(), 2);

        Color colorYA = picture().get(col, row + 1);
        Color colorYB = picture().get(col, row - 1);
        double yGradientSq = Math.pow(colorYA.getRed() - colorYB.getRed(), 2) +
                Math.pow(colorYA.getGreen() - colorYB.getGreen(), 2) +
                Math.pow(colorYA.getBlue() - colorYB.getBlue(), 2);
        return Math.sqrt(xGradientSq + yGradientSq);
    }

    public void removeHorizontalSeam(int[] horizontalSeam) {

    }

    public void removeVerticalSeam(int[] verticalSeam) {
    }

    public Picture picture() {
        return picture;
    }

    public static void main(String[] args) {
        Picture pic = new Picture("6x5.png");
        SeamCarver seamCarver = new SeamCarver(pic);
        int width = pic.width();
        int height = pic.height();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StdOut.printf(" %.2f", seamCarver.energy(j, i));
            }
            StdOut.println();
        }

        int[] verticalSeam = seamCarver.findVerticalSeam();
        for (int i : verticalSeam) StdOut.print(i + " ");
    }
}
