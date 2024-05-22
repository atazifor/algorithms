/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class CopySortedArray {
    public static void main(String[] args) {
        int[] a = new int[] { 2, 6, 9, 11, 12, 1, 3, 4, 5, 7 };
        int n = a.length / 2;
        int[] aux = new int[n];
        for (int i = 0; i < n; i++) aux[i] = a[i];
        int i = 0;
        int j = n;
        int k = 0;
        while (i < n && j < 2 * n) {
            if (aux[i] < a[j])
                a[k++] = aux[i++];
            else
                a[k++] = a[j++];
        }
        while (i < n) {
            a[k++] = aux[i++];
        }
        for (int p : a) {
            System.out.print(" " + p);
        }
    }
}
