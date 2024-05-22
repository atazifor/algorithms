/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class DutchNationalFlag {
    public static void main(String[] args) {
        int[] arr = new int[] { 1, 1, 0, 2, 0, 2, 1, 0, 0, 2, 1 };
        int l = 0;
        int r = arr.length - 1;
        int i = 0;
        while (i <= r) {
            if (arr[i] == 0) {
                swap(arr, l, i);
                l++;
                i++;
            }
            else if (arr[i] == 2) {
                swap(arr, r, i);
                r--;
            }
            else {
                i++;
            }
        }
        for (int j : arr)
            System.out.print(" " + j);
    }

    public static void swap(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }
}
