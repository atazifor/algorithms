import java.util.Arrays;

/**
 * Given an array n 64-bit integers and a target value
 * T, determine whether there are two distinct integers
 * i and j such that ai + aj = T
 * Your algorithm should run in linear time in the worst case.
 */
public class Linear2WaySumModuloRadixSort {
    //since linear time algorithm. we do index sort with radix 64
    //then binary sear T-aj

    public static boolean isSum(long[] a, long T) {

        isolateAndSort(a);
        int l = 0;
        int r = a.length - 1;
        while(r > l) {
            long sum = a[l] + a[r];
            if(sum > T) r--;
            else if(sum < T) l++;
            else {
                System.out.println(String.format("[%s + %s] = %s", a[l], a[r], T));
                return true;
            }
        }
        return false;
    }

    public static void isolateAndSort(long[] a) {
        int m = 0;
        int N = a.length;
        for(int i = 0; i < N; i++)
            if(a[i] < 0) m++;
        long[] pos = new long[N-m];
        long[] neg = new long[m];
        int i = 0;
        int j = 0;
        for(int k = 0; k < N; k++) {
            if(a[k] >= 0) pos[i++] = a[k];
            else neg[j++] = -1 * a[k];
        }
        sort(pos);
        sort(neg); //ascending positive order
        i = 0;
        for(int k = neg.length-1; k >= 0; k--) //reverse to get asending negative order
            a[i++] = -1*neg[k];
        for(int k = 0; k < pos.length; k++)
            a[i++] = pos[k];
    }

    public static void sort(long[] a) {
        int R = 10;
        int N = a.length;
        long[] aux = new long[N];

        long max = max(a);
        long divisor = 1;
        while(max/divisor > 0) {

            int[] count = new int[R+1];
            //count frequencies
            for(int i = 0; i < N; i++)
                count[(int)(a[i]/divisor)%10 + 1]++;
            for(int r=0; r < R; r++) //starting position for each digit
                count[r+1] += count[r];
            for(int i = 0; i < N; i++)
                aux[count[(int)(a[i]/divisor)%10]++] = a[i];
            for(int i = 0; i < N; i++)
                a[i] = aux[i];

            divisor *= 10;
        }

    }

    public static long max(long[] a) {
        long max = Long.MIN_VALUE;
        for(long l: a) {
            max = Math.max(max, l);
        }
        return max;
    }

    public static void main(String[] args) {
        long[] numbers = {1, 2345, 78, -345, 123456, 56789, -1, 345};
        isolateAndSort(numbers);
        System.out.println(Arrays.toString(numbers));
        System.out.println(isSum(numbers, 50));
        System.out.println(isSum(numbers, 79));
        System.out.println(isSum(numbers, 85));
        System.out.println(isSum(numbers, 0));
    }

}
