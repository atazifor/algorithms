/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    // constructs an empty set  of points
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it's not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null parameter not allowed");
        if (!contains(p)) {
            points.add(p);
        }
    }

    // does the set contain p
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null parameter not allowed");
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null parameter not allowed");
        List<Point2D> intersectionPoints = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) intersectionPoints.add(p);
        }
        return intersectionPoints;
    }

    // a nearest neighbor in the set to the point p; null if  the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null parameter not allowed");
        double min = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D other : points) {
            if (p.distanceTo(other) < min) {
                min = p.distanceTo(other);
                nearest = other;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        // brute.draw();
        System.out.println("brute.nearest(new Point2D(0.400, 0.208)) = " + brute.nearest(
                new Point2D(0.400, 0.208)));
    }
}
