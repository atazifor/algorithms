/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class BinaryTree {

    Node root;

    public BinaryTree() {


    }

    public boolean isBst() {
        // use what ever is is absolute min and max for the key.
        // for Integer, it's Integer.MIN_VALUE, Integer.MAX_VALUE
        return isBst(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBst(Node x, int min, int max) {
        if (x == null) return true;
        if (x.key > min && x.key < max)
            return isBst(x.left, min, x.key) && isBst(x.right, x.key, max);
        else return false;
    }

    private class Node {
        int key;
        Node left;
        Node right;
        int size; // size of subtree containing this node.

        public Node(int key, int size) {
            this.key = key;
            this.size = size;
        }

    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.root = bt.new Node(20, 1);
        bt.root.left = bt.new Node(9, 1);
        bt.root.right = bt.new Node(35, 1);
        BinaryTree.Node x = bt.root.left;
        x.left = bt.new Node(7, 1);
        x.right = bt.new Node(15, 1);
        // x = x.left;
        // x.right = bt.new Node(12, 1);
        x = x.right;
        x.left = bt.new Node(11, 1);
        x.right = bt.new Node(18, 1);
        System.out.println("bt.isBst() = " + bt.isBst());
    }
}
