/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class CountInversions {
    public static void main(String[] args) {
        CountInversions countInversions = new CountInversions();

        int[] a = new int[] { 3, 1, 8, 4, 2 };
        countInversions.sort(a);
        for (int p : a) {
            System.out.print(" " + p);
        }
        System.out.println(" ");
        System.out.print(" inversions " + countInversions.count);
    }

    int count;

    public void sort(int[] a) {
        count = 0;
        int[] aux = new int[a.length];
        sort(aux, a, 0, a.length - 1);
    }

    private void sort(int[] aux, int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);
        merge(aux, a, lo, mid, hi);
    }

    private void merge(int[] aux, int[] a, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++)
            aux[i] = a[i];
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[i] < aux[j]) a[k] = aux[i++];
            else {
                a[k] = aux[j++];
                count += (mid + 1 - i);
            }
        }
    }
}
