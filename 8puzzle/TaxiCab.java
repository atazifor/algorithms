/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A taxicab is a number that can be expressed as the sum of cubes of 2 positive integers
 * in two different ways i.e. a3 + b3 = c3 + d3.
 */
public class TaxiCab {

    private class Pair implements Comparable<Pair> {
        long sum;
        int a;
        int b;

        public Pair() {

        }

        public Pair(long sum, int a, int b) {
            this.sum = sum;
            this.a = a;
            this.b = b;
        }

        public int compareTo(Pair o) {
            return Long.compare(this.sum, o.sum);
        }
    }

    public List<Long> bruteForceTaxiCab(int n) {
        List<Long> result = new ArrayList<>();
        Pair[] sums = new Pair[n * (n + 1) / 2];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int ith = i + 1;
                int jth = j + 1;
                sums[index++] = new Pair((long) ith * ith * ith + (long) jth * jth * jth, ith,
                                         jth);
            }
        }
        Arrays.sort(sums);
        int count = 1;
        for (int i = 1; i < sums.length; i++) {
            if (sums[i].sum == sums[i - 1].sum) {
                count++;
                if (count > 1) {
                    result.add(sums[i].sum);
                }
            }
            else {
                count = 1;
            }
        }
        return result;
    }

    public List<Long> priorityQueueTaxiCab(int n) {
        List<Long> result = new ArrayList<>();
        MinPQ<Pair> minPQ = new MinPQ<>();
        for (int i = 1; i <= n; i++) minPQ.insert(new Pair(i * i * i + i * i * i, i, i));
        Pair previous = null;
        while (!minPQ.isEmpty()) {
            Pair current = minPQ.delMin();
            long csum = current.sum;
            int ca = current.a;
            int cb = current.b;
            if (previous != null && csum == previous.sum) {
                result.add(csum);
                System.out.println(
                        String.format("%s^3 + %s^3 = %s^3 + %s^3 = %s", previous.a, previous.b, ca,
                                      cb, csum));
            }
            if (cb + 1 <= n) {
                minPQ.insert(new Pair(ca * ca * ca + (cb + 1) * (cb + 1) * (cb + 1), ca, cb + 1));
            }
            previous = current;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("bruteForceTaxiCab = " + new TaxiCab().bruteForceTaxiCab(16));
        System.out.println("priorityQueueTaxiCab = " + new TaxiCab().priorityQueueTaxiCab(16));
    }
}
