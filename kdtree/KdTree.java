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
    Node root;

    public KdTree() {

    }

    private class Node {
        int level;
        Point2D point;
        Node left;
        Node right;

        // double xmin, xmax, ymin, ymax;

        public Node(Point2D point, int level) {
            this.point = point;
            this.level = level;
        }
    }

    public Point2D nearest(Point2D p) {
        return null;
    }

    public void insert(Point2D p) {
        root = insert(root, p, 0);
    }

    public void draw() {
        draw(root, 0, 1, 0, 1);
    }

    private void draw(Node node, double xMin, double xMax, double yMin, double yMax) {
        if (node == null) return;
        Point2D start;
        Point2D end;
        boolean isEvenLevel = node.level % 2 == 0 ? true : false;
        if (isEvenLevel) { // vertical line
            start = new Point2D(node.point.x(), yMin);
            end = new Point2D(node.point.x(), yMax);
        }
        else {// horizontal  line
            start = new Point2D(xMin, node.point.y());
            end = new Point2D(xMax, node.point.y());
        }
        // set point color
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        StdDraw.setPenRadius(0.002);
        // set line color
        if (isEvenLevel) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLUE);
        start.drawTo(end);
        if (node.left != null) {
            // current node is even (vertical), then next must be odd (horizontal)
            if (isEvenLevel) draw(node.left, xMin, node.point.x(), yMin, yMax);
            else draw(node.left, xMin, xMax, yMin, node.point.y());
        }
        if (node.right != null) {
            if (isEvenLevel) draw(node.right, node.point.x(), xMax, yMin, yMax);
            else draw(node.right, xMin, xMax, node.point.y(), yMax);
        }

    }

    private Node insert(Node node, Point2D point, int level) {
        if (node == null) return new Node(point, level);
        int cmp = 0;
        // even levels use x-coordinate
        boolean isEvenLevel = level % 2 == 0;
        if (isEvenLevel) cmp = Double.compare(point.x(), node.point.x());
        else cmp = Double.compare(point.y(), node.point.x());
        if (cmp < 0) {
            node.left = insert(node.left, point, level + 1);
        }
        else if (cmp > 0) {
            node.right = insert(node.right, point, level + 1);
        }
        else {
            node.point = point;
        }
        return node;
    }

    public Point2D[] range(RectHV rect) {
        List<Point2D> result = new ArrayList<>();
        // range(root, rect, result);
        return null;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        kdTree.draw();
    }

}
