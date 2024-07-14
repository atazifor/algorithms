import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CyclicRotations {
    public static List<String[]> cyclicRotations(String[] strings, int L) {
        List<String[]> rotations  = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for(String s: strings) {
            map.put(s, s);
        }

        for(String  string: strings) {
            String s  = string + string; //doubled string must contain rotation
            for(int i = 0; i < L; i++) {
                String sub = s.substring(i, i + L);
                if(!sub.equals(string) && map.containsKey(sub))
                    rotations.add(new String[]{string, sub});
            }
        }
        return rotations;
    }

    public static void main(String[] args) {
        String[] strings = {"algorithms", "polynomial", "sortsuffix", "boyermoore",
                "structures", "minimumcut", "suffixsort", "stackstack",
                "binaryheap", "digraphdfs", "stringsort", "digraphbfs"};
        List<String[]> rotations = cyclicRotations(strings,  10);
        for(String[] pair: rotations) {
            System.out.println(String.format("[%s,%s]", pair[0], pair[1]));
        }
    }
}
