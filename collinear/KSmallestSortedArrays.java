/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class KSmallestSortedArrays {

    public static int kthsmallest(int[] a, int[] b, int k) {
        int lenA = a.length;
        int lenB = b.length;
        if (lenB < lenA) return kthsmallest(b, a, k);

        int low = 0;
        int high = Math.min(lenA, k);
        while (low <= high) {
            int partitionA = (low + high) / 2;
            int partitionB = k - partitionA;
            int maxLeft = partitionA == 0 ? Integer.MIN_VALUE : a[partitionA - 1];
            int minLeft = partitionA == lenA ? Integer.MAX_VALUE : a[partitionA];
            int maxRight = partitionB == 0 ? Integer.MIN_VALUE : b[partitionB - 1];
            int minRight = partitionB == lenB ? Integer.MAX_VALUE : b[partitionB];
            if (maxLeft <= minRight && maxRight <= minLeft)
                return Math.max(maxLeft, maxRight);
            else if (maxLeft > minRight) {
                high = partitionA - 1;
            }
            else {
                low = partitionA + 1;
            }
        }
        throw new IllegalArgumentException("Value of k is invalid or array is not sorted");
    }

    public static void main(String[] args) {
        int[] a = { 2, 4, 6, 8 };
        int[] b = { 3, 5, 7, 9 };
        int fifthSmallest = kthsmallest(a, b, 5);
        System.out.println("fifthSmallest = " + fifthSmallest);

        int[] c = { 1, 3, 5, 7, 9 };
        fifthSmallest = kthsmallest(a, c, 5);
        System.out.println("fifthSmallest = " + fifthSmallest);
    }
}
