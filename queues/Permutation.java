/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.InputMismatchException;

public class Permutation {
    public static void main(String[] args) {
        if (args[0] == null) throw new InputMismatchException("need one integer");
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            // use only one Deque or RandomizedQueue object of maximum size at most k.
            q.enqueue(s);
        }
        for (int i = 0; i < k; i++)
            StdOut.println(q.dequeue());
    }
}
