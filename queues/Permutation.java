/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.InputMismatchException;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        if (args[0] == null) throw new InputMismatchException("need one integer");
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
        }
        int n = q.size();
        if (k >= 0 && k <= n) {
            int count = 0;
            for (Iterator<String> it = q.iterator(); it.hasNext() && count++ < k; )
                StdOut.println(it.next());
        }
    }
}
