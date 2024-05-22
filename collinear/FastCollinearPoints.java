/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private int segmentCount;

    public FastCollinearPoints(
            Point[] points) {   // finds all line segments containing 4 or more points
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

    public int numberOfSegments() {// the number of line segments
        return segmentCount;
    }

    public LineSegment[] segments() {// the line segments
        int n = points.length;
        int maxSegments = n * (n - 1) * (n - 2) * (n - 3) / 24;
        LineSegment[] temp = new LineSegment[maxSegments];

        Point[] copyOfPoints = points.clone();
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            Arrays.sort(copyOfPoints, p.slopeOrder());
            double slope = p.slopeTo(copyOfPoints[1]);
            int count = 1;
            Point element = null;
            for (int j = 1; j < n; j++) {
                if (slope == p.slopeTo(copyOfPoints[j])) count++; // compare to previous slope
                else {
                    if (count >= 3) { // longest so far
                        Arrays.sort(copyOfPoints, j - count, j); // sort points by natural order
                        element = copyOfPoints[j - 1];
                        if (p.compareTo(element) < 0) { // only add other point p->q->r->s [p<s]
                            // DON'T add sub-path if p->q->r->s then [q->r->s] should not be included
                            if (p.compareTo(copyOfPoints[j - count]) <= 0)
                                temp[segmentCount++] = new LineSegment(p, element);
                        }
                    }
                    count = 1; // reset
                    slope = p.slopeTo(copyOfPoints[j]); // new slope to compare against
                }
            }
            if (count >= 3) { // if longest group of slopes equal to p occur at the end
                Arrays.sort(copyOfPoints, n - count, n);// sort points by natural order
                element = copyOfPoints[n - 1];
                if (p.compareTo(element) < 0) { // only add other point p->q->r->s [p<s]
                    // DON'T add sub-path if p->q->r->s then [q->r->s] should not be included
                    if (p.compareTo(copyOfPoints[n - count]) <= 0)
                        temp[segmentCount++] = new LineSegment(p, element);
                }
            }
        }
        LineSegment[] segments = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++)
            segments[i] = temp[i];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
