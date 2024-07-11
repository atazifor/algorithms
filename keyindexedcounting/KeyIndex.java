public class KeyIndex {

    /**
     * sort an array of single digit numbers.
     * Radix is 10 (0 to 9)
     * @param a
     */
    public static void keyIndexSort(int[] a) {
        int R = 10;
        int N = a.length;
        int[] count = new int[R+1];
        int[] aux = new int[N];
        //count frequencies
        for(int i = 0; i < N; i++)
            count[a[i] + 1]++;
        //compute cumulates (which gives position of each digit in encountered order)
        for(int r = 0; r < R; r++)
            count[r+1] += count[r];
        for(int i = 0; i < N; i++)
            aux[count[a[i]]++] = a[i];
        for(int i = 0; i < N; i++)
            a[i] = aux[i];
    }

    /**
     * sort string of size 1. Assume 256 ascii characters
     * @param a
     */
    public static void strIndexSort(String[] a) {
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];
        int[] count = new int[R+1];
        for(int i = 0; i < N; i++)
            count[a[i].charAt(0) + 1]++;
        for(int r = 0; r < R; r++)
            count[r+1] += count[r];
        for(int i = 0; i < N; i++)
            aux[count[a[i].charAt(0)]++] = a[i];
        for(int i = 0; i < N; i++)
            a[i] = aux[i];
    }


    public static void main(String[] args) {
        int[] a = {2,5,7,1,9,0,3,7,2,0,5,6,1,0,5,3,6,8};
        keyIndexSort(a);
        for(int i: a)
            System.out.print(" " + i);
        System.out.println();
        System.out.println("a".charAt(0)+0);
        System.out.println("A".charAt(0)+0);
        System.out.println("z".charAt(0)+0);
        System.out.println("Z".charAt(0)+0);

        String[] s = {"h", "r", "v", "a", "H", "Z"};
        strIndexSort(s);
        for(String i: s)
            System.out.print(" " + i);
        System.out.println();
    }
}
