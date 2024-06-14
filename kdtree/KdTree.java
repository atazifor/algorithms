/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root; // 8 bytes for inner class
    private int N;

    public KdTree() {

    }

    private class Node { // 16 bytes memory overhead
        int level;
        Point2D point;// 32 bytes
        Node left;// 8 bytes object reference
        Node right;// 8 bytes object reference

        RectHV rectHV; // 16 bytes object overhead, 4x8 instance variables + 8 padding = 56 bytes

        public Node(Point2D point, int level, double xmin, double xmax, double ymin, double ymax) {
            this.point = point; // object reference 8 bytes
            this.level = level;
            this.rectHV = new RectHV(xmin, ymin, xmax, ymax); // object reference 8 bytes
        }
    }

    public Point2D nearest(Point2D p) {
        Point2D nearestSoFar = new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        nearestSoFar = nearest(root, p, nearestSoFar);
        return nearestSoFar;
    }

    private Point2D nearest(Node node, Point2D queryPoint, Point2D nearestSoFar) {
        if (node == null) return nearestSoFar;
        if (node.point.distanceTo(queryPoint) < nearestSoFar.distanceTo(queryPoint)) {
            nearestSoFar = node.point;
        }
        Node first = node.left;
        Node second = node.right;
        if (node.right != null && (node.left == null
                || node.left.rectHV.distanceTo(queryPoint) > node.right.rectHV.distanceTo(
                queryPoint))) {
            first = node.right;
            second = node.left;
        }
        if (first != null
                && first.rectHV.distanceTo(queryPoint) < nearestSoFar.distanceTo(
                queryPoint))
            nearestSoFar = nearest(first, queryPoint, nearestSoFar);
        if (second != null
                && second.rectHV.distanceTo(queryPoint) < nearestSoFar.distanceTo(queryPoint))
            nearestSoFar = nearest(second, queryPoint, nearestSoFar);
        return nearestSoFar;
    }

    public void insert(Point2D p) {
        root = insert(root, p, 0, 0, 1, 0, 1);
        N++;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        Point2D start;
        Point2D end;
        boolean isEvenLevel = node.level % 2 == 0 ? true : false;
        if (isEvenLevel) { // vertical line
            start = new Point2D(node.point.x(), node.rectHV.ymin());
            end = new Point2D(node.point.x(), node.rectHV.ymax());
        }
        else {// horizontal  line
            start = new Point2D(node.rectHV.xmin(), node.point.y());
            end = new Point2D(node.rectHV.xmax(), node.point.y());
        }
        // set point color
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        node.point.draw();
        StdDraw.setPenRadius(0.002);
        // set line color
        if (isEvenLevel) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLUE);
        start.drawTo(end);
        if (node.left != null) {
            // current node is even (vertical), then next must be odd (horizontal)
            if (isEvenLevel) draw(node.left);
            else draw(node.left);
        }
        if (node.right != null) {
            if (isEvenLevel) draw(node.right);
            else draw(node.right);
        }

    }

    private Node insert(Node node, Point2D point, int level, double xmin, double xmax, double ymin,
                        double ymax) {
        if (node == null) return new Node(point, level, xmin, xmax, ymin, ymax);
        int cmp = 0;
        // even levels use x-coordinate
        boolean isEvenLevel = level % 2 == 0;
        if (isEvenLevel) cmp = Double.compare(point.x(), node.point.x());
        else cmp = Double.compare(point.y(), node.point.y());
        if (cmp < 0) {
            if (isEvenLevel)
                node.left = insert(node.left, point, level + 1, xmin, node.point.x(), ymin, ymax);
            else
                node.left = insert(node.left, point, level + 1, xmin, xmax, ymin, node.point.y());
        }
        else if (cmp > 0) {
            if (isEvenLevel)
                node.right = insert(node.right, point, level + 1, node.point.x(), xmax, ymin, ymax);
            else
                node.right = insert(node.right, point, level + 1, xmin, xmax, node.point.y(), ymax);
        }
        else {
            node.point = point;
        }
        return node;
    }

    public boolean contains(Point2D point2D) {
        return contains(root, point2D, 0, 0, 1, 0, 1);
    }

    private boolean contains(Node node, Point2D point, int level, double xmin, double xmax,
                             double ymin,
                             double ymax) {
        if (node == null) return false;

        int cmp = 0;
        // even levels use x-coordinate
        boolean isEvenLevel = level % 2 == 0;
        if (isEvenLevel) cmp = Double.compare(point.x(), node.point.x());
        else cmp = Double.compare(point.y(), node.point.y());
        if (cmp < 0) {
            if (isEvenLevel)
                return contains(node.left, point, level + 1, xmin, node.point.x(), ymin, ymax);
            else
                return contains(node.left, point, level + 1, xmin, xmax, ymin, node.point.y());
        }
        else if (cmp > 0) {
            if (isEvenLevel)
                return contains(node.right, point, level + 1, node.point.x(), xmax, ymin, ymax);
            else
                return contains(node.right, point, level + 1, xmin, xmax, node.point.y(), ymax);
        }
        else {
            return true;
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new ArrayList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node node, RectHV rectHV, List<Point2D> intersectionPoints) {
        if (node == null) return;
        if (rectHV.contains(node.point)) intersectionPoints.add(node.point);

        if (node.left != null && rectHV.intersects(node.left.rectHV))
            range(node.left, rectHV, intersectionPoints);
        if (node.right != null && rectHV.intersects(node.right.rectHV))
            range(node.right, rectHV, intersectionPoints);
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        RectHV rectHV = new RectHV(0.1, 0.2, 0.6, 0.5);
        System.out.println(" Points in range: " + rectHV.toString());
        Iterable<Point2D> range = kdTree.range(rectHV);
        for (Point2D point2D : range) {
            System.out.print(" " + point2D);
        }
        System.out.println();
        System.out.println("nearest [0.4, 0.6] is: " + kdTree.nearest(new Point2D(0.4, 0.6)));
        System.out.println("kdTree contains [0.5, 0.4] " + kdTree.contains(new Point2D(0.5, 0.4)));
        System.out.println("kdTree contains [0.9, 0.6] " + kdTree.contains(new Point2D(0.9, 0.6)));
        System.out.println("kdTree contains [0.9, 0.1] " + kdTree.contains(new Point2D(0.9, 0.1)));
        System.out.println("About to Draw Now");
        rectHV.draw();
        kdTree.draw();
    }

}
