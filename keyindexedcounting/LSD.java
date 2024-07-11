/**
 * fixed length string sort
 */
public class LSD {
    /**
     *
     * @param a array of strings, all of the same length, W
     * @param W fixed width of each string in a
     */
    public static void sort(String[] a, int W) {
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];

        for(int d = W-1; d >= 0; d--) {
            int[] count = new int[R+1];
            for(int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            for(int r = 0; r < R; r++)
                count[r+1] += count[r];
            for(int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            for(int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        String[] a = {"ced", "add", "jef", "feg"};
        sort(a, 3);
        for(String i: a)
            System.out.print(" " + i);
        System.out.println();
    }
}
