/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private int segmentCount;

    public BruteCollinearPoints(Point[] points) {// finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException(
                "argument to BruteCollinearPoints constructor is null");
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("one of the points in the array is null");
        }
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            copy[i] = points[i];
        Arrays.sort(copy);
        for (int i = 1; i < points.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0)
                throw new IllegalArgumentException("duplicate points provided");
        }
        this.points = points;
        segmentCount = 0;
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {// the line segments
        int n = points.length;
        // max number of segments n combination 4
        int maxSegments = n * (n - 1) * (n - 2) * (n - 3) / 24;
        LineSegment[] temp = new LineSegment[maxSegments];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            for (int j = i + 1; j < n; j++) {
                Point q = points[j];
                for (int k = j + 1; k < n; k++) {
                    Point r = points[k];
                    for (int l = k + 1; l < n; l++) {
                        Point s = points[l];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            Point[] collinearPoints = new Point[] { p, q, r, s };
                            Arrays.sort(collinearPoints);
                            System.out.print("p = " + p);
                            System.out.print(" q = " + q);
                            System.out.print(" r = " + r);
                            System.out.print(" s = " + s);
                            System.out.println("");
                            segmentCount++;
                            temp[counter++] = new LineSegment(collinearPoints[0],
                                                              collinearPoints[3]);
                        }
                    }
                }
            }
        }
        LineSegment[] segments = new LineSegment[counter];
        for (int i = 0; i < counter; i++) segments[i] = temp[i];
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
