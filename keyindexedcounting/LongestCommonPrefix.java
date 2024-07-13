import java.util.Arrays;
import java.util.Collections;

public class LongestCommonPrefix {
    
    public static String lcp(String s) {
        String[] suffixes = suffixes(s);
        Arrays.sort(suffixes);
        String maxPrefix = "";
        int maxLength = 0;
        for(int i = 0;  i < suffixes.length-1; i++) {
            int len = lcp(suffixes[i], suffixes[i+1]);
            if(len > maxLength)  {
                maxLength  = len;
                maxPrefix = suffixes[i].substring(0, len);
            }
        }
        return maxPrefix;
    }
    
    public static String[] suffixes(String s) {
        int N  = s.length();
        String[] suffixes = new String[N];
        for(int i = 0; i < N; i++) {
          suffixes[i] = s.substring(i, N);   
        }
        return suffixes;
    }
    
    public static int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for(int i = 0; i < N; i++) {
            if(s.charAt(i) != t.charAt(i))
                return i;
        }
        return N;
    }
    
    public static void main(String[] args) {
        String text = "a a c a a g t t t a c a a g c a t g a t g ctgtacta g g a g a g t t a t a c t g g t c g t c a aacctgaa c c t a a t c c t t g t g t g t a c a c a cactacta c t g t c g t c g t c a t a t a t c g a g atcatcga a c c g g a a g g c c g g a c a a g g c g gggggtat a g a t a g a t a g a c c c c t a g a t a cacataca t a g a t c t a g c t a g c t a g c t c a tcgataca c a c t c t c a c a c t c a a g a g t t a tactggtc a a c a c a c t a c t a c g a c a g a c g accaacca g a c a g a a a a a a a a c t c t a t a t ctataaaa";

        System.out.println("lcp(text) = " + lcp(text));
    }
}
