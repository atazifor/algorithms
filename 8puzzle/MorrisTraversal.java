/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

/**
 * Inorder traversal of binary tree using constant space (no recursion)
 * if current node has no left child visit the current node and move to the right child
 * else get predecessor of current's node left child (far right  of  the left  child):
 * if predecessor does not have a right  child(predecessor.right==  null), make it point to current
 * node
 * elseif predecessor.right != null, make it null (go back to original state), visit current node
 * and update current node
 */
public class MorrisTraversal {
    
    public void inorderTraversal(Node x) {
        Node current = x;
        while (current != null) {
            if (current.left == null) {
                System.out.println(current.key);
                current = current.left;
            }
            else {
                Node predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = current;
                    current = current.left;
                }
                else {
                    predecessor.right = null;
                    System.out.println(current.key);
                    current = current.right;
                }
            }
        }
    }

    private class Node {
        int key;
        Node left;
        Node right;

        public Node(int key) {
            this.key = key;
        }

    }

    public static void main(String[] args) {

    }
}
