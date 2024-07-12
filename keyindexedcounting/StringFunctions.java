public class StringFunctions {
    public static int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for(int i = 0; i < N; i++) {
            if(s.charAt(i) != t.charAt(i))
                return i;
        }
        return N;
    }

    public static String[] suffixes(String s) {
        int N = s.length();
        String[] suffixes = new String[N];
        for(int i = 0; i < N; i++) {
            suffixes[i] = s.substring(i, N);
        }
        return suffixes;
    }

    public static void dnf(int[] a) {
        int lt = 0;
        int gt = a.length-1;
        int j = 0;
        while(j <= gt) {
            if(a[j] < 1) exch(a, j++, lt++);
            else if(a[j] == 1) j++;
            else //a[j] > 1
                exch(a, j, gt--);
        }
    }

    public static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        System.out.println("lcp(\"prenuptial\", \"prenatal\") = " + lcp("prenuptial", "prenatal"));
        System.out.println("===\nSuffixes of prenuptial\n===");
        for(String s: suffixes("prenuptial")) {
            System.out.println(s);
        }

        int[] a = {2,2,2,1,0,0,1,1,0,0,2,0};
        dnf(a);
        System.out.println("===\nDutch National Flag\n===");
        for(int i: a) {
            System.out.print(" " + i);
        }
        System.out.println();


    }
}
