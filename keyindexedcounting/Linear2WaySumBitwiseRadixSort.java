import java.util.Arrays;

/**
 * Given an array n 64-bit integers and a target value
 * T, determine whether there are two distinct integers
 * i and j such that ai + aj = T
 * Your algorithm should run in linear time in the worst case.
 */
public class Linear2WaySumBitwiseRadixSort {
    //since linear time algorithm. we do index sort with radix 64
    //then binary sear T-aj

    public static boolean isSum(long[] a, long T) {

        sort(a);
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



    //extract 8 bits at a time
    public static void sort(long[] a) {
        int R = 256;
        int N = a.length;
        long[] aux = new long[N];
        int shift = 0;
        int mask = 0xFF;
        while(shift < 64) {
            int[] count = new int[R+1];
            for(int i=0; i<N; i++) {
                int digit = (int)(a[i] >> shift) & mask;
                count[digit + 1]++;
            }
            for(int r=0; r<R; r++)
                count[r+1] += count[r];

            for(int i=0; i<N; i++) {
                int digit = (int)(a[i] >> shift) & mask;
                aux[count[digit]++] = a[i];
            }

            for(int i=0; i<N; i++)
                a[i] = aux[i];
            shift += 8;
        }

        //handle negative values
        int j = 0;
        for(int i = 0; i < N; i++) {
            if(a[i] < 0) aux[j++] = a[i];
        }
        for(int i = 0; i < N; i++) {
            if(a[i] >= 0) aux[j++] = a[i];
        }
        for(int i = 0; i < N; i++)
            a[i] = aux[i];

    }


    public static void main(String[] args) {
        long[] numbers = {1, 2345, 78, -345, 123456, 56789, -1, 345};
        sort(numbers);
        System.out.println(Arrays.toString(numbers));
        System.out.println(isSum(numbers, 50));
        System.out.println(isSum(numbers, 79));
        System.out.println(isSum(numbers, 85));
        System.out.println(isSum(numbers, 0));
    }

}
