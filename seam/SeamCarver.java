/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    public int[] findHorizontalSeam() {
        double[][] disTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];

        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                disTo[row][col] = Double.POSITIVE_INFINITY;
            }
        }

        for (int row = 0; row < height(); row++) {
            disTo[row][0] = energy(0, row); // initialize the top col
            edgeTo[row][0] = row;
        }

        // process in topological order
        for (int col = 0; col < width() - 1; col++) {
            for (int row = 0; row < height(); row++) {
                for (int[] w : adjH(col, row)) {
                    relaxH(row, col, w[1], w[0], disTo, edgeTo);
                }
            }
        }

        double minDistance = Double.POSITIVE_INFINITY;
        int minRow = 0;
        for (int row = 0; row < height(); row++) {
            if (disTo[row][width() - 1] < minDistance) {
                minDistance = disTo[row][width() - 1];
                minRow = row;
            }
        }
        int[] seam = new int[width()];
        int row = minRow;
        for (int col = width() - 1; col >= 0; col--) {
            seam[col] = row;
            row = edgeTo[row][col];
        }
        return seam;
    }

    public int[] findVerticalSeam() {
        double[][] disTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];

        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                disTo[row][col] = Double.POSITIVE_INFINITY;
            }
        }

        for (int col = 0; col < width(); col++) {
            disTo[0][col] = energy(col, 0); // initialize the top row
            edgeTo[0][col] = col;
        }

        // process vertices in topological order
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                for (int[] w : adjV(col, row)) {
                    relaxV(row, col, w[1], w[0], disTo, edgeTo);
                }
            }
        }

        double minDistance = Double.POSITIVE_INFINITY;
        int minCol = 0;
        for (int col = 0; col < width(); col++) {
            if (disTo[height() - 1][col] < minDistance) {
                minDistance = disTo[height() - 1][col];
                minCol = col;
            }
        }

        int[] minPath = new int[height()];
        int col = minCol;
        for (int row = height() - 1; row >= 0; row--) {
            minPath[row] = col;
            col = edgeTo[row][col];
        }
        return minPath;
    }

    private void relaxV(int currentRow, int currentCol, int nextRow, int nextCol,
                        double[][] disTo, int[][] edgeTo) {
        double energy = energy(nextCol, nextRow);
        if (disTo[nextRow][nextCol] > disTo[currentRow][currentCol] + energy) {
            disTo[nextRow][nextCol] = disTo[currentRow][currentCol] + energy;
            edgeTo[nextRow][nextCol] = currentCol;
        }
    }

    private void relaxH(int currentRow, int currentCol, int nextRow, int nextCol,
                        double[][] disTo, int[][] edgeTo) {
        double energy = energy(nextCol, nextRow);
        if (disTo[nextRow][nextCol] > disTo[currentRow][currentCol] + energy) {
            disTo[nextRow][nextCol] = disTo[currentRow][currentCol] + energy;
            edgeTo[nextRow][nextCol] = currentRow;
        }
    }

    private Iterable<int[]> adjV(int col, int row) {
        List<int[]> adj = new ArrayList<>();
        if (col - 1 >= 0 && row + 1 < height()) {
            adj.add(new int[] { col - 1, row + 1 });
        }
        if (row + 1 < height()) {
            adj.add(new int[] { col, row + 1 });
        }
        if (col + 1 < width() && row + 1 < height())
            adj.add(new int[] { col + 1, row + 1 });
        return adj;
    }

    private Iterable<int[]> adjH(int col, int row) {
        List<int[]> adj = new ArrayList<>();
        if (col + 1 <= width() - 1 && row + 1 <= height() - 1)
            adj.add(new int[] { col + 1, row + 1 });
        if (col + 1 <= width() - 1)
            adj.add(new int[] { col + 1, row });
        if (col + 1 <= width() - 1 && row - 1 >= 0)
            adj.add(new int[] { col + 1, row - 1 });
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
        if (horizontalSeam == null) throw new IllegalArgumentException("null argument not allowed");
        if (horizontalSeam.length != width())
            throw new IllegalArgumentException("invalid horizontal seam size");
        // Check if the seam is valid
        for (int col = 0; col < width(); col++) {
            if (horizontalSeam[col] < 0 || horizontalSeam[col] >= height()) {
                throw new IllegalArgumentException("Invalid seam index");
            }
            if (col > 0 && Math.abs(horizontalSeam[col] - horizontalSeam[col - 1]) > 1) {
                throw new IllegalArgumentException(
                        "Invalid seam: consecutive indices differ by more than 1");
            }
        }

        Picture p = new Picture(width(), height() - 1);
        for (int col = 0; col < width(); col++) {
            int newRow = 0;
            for (int row = 0; row < height(); row++) {
                if (row != horizontalSeam[col]) {
                    p.set(col, newRow++, picture().get(col, row));
                }
            }
        }
        picture = p;
    }

    public void removeVerticalSeam(int[] verticalSeam) {
        if (verticalSeam == null) throw new IllegalArgumentException("null argument not allowed");
        if (verticalSeam.length != height())
            throw new IllegalArgumentException("invalid horizontal seam size");
        for (int row = 0; row < height(); row++) {
            if (verticalSeam[row] < 0 || verticalSeam[row] >= width())
                throw new IllegalArgumentException("Invalid seam index");
            if (row > 0 && Math.abs(verticalSeam[row] - verticalSeam[row - 1]) > 1)
                throw new IllegalArgumentException(
                        "Invalid seam: consecutive indices differ by more than 1");
        }

        Picture p = new Picture(width() - 1, height());
        for (int row = 0; row < height(); row++) {
            int newCol = 0;
            for (int col = 0; col < width(); col++) {
                if (col != verticalSeam[row]) {
                    p.set(newCol++, row, picture().get(col, row));
                }
            }
        }
        picture = p;
    }

    public Picture picture() {
        return picture;
    }

    public static void main(String[] args) {
        Picture pic = new Picture("12x10.png");
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
        System.out.println("Vertical Seam");
        for (int i : verticalSeam) StdOut.print(i + " ");
        int[] horizontalSeam = seamCarver.findHorizontalSeam();
        System.out.println("\nHorizontal Seam");
        for (int i : horizontalSeam) StdOut.print(i + " ");
        // seamCarver.removeVerticalSeam(verticalSeam);
        seamCarver.removeHorizontalSeam(horizontalSeam);
    }
}
