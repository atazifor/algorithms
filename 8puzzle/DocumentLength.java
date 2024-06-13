/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

/**
 * Design an algorithm that takes a sequence of n document words
 * and a sequence of m query words and find the shortest interval in
 * which the m query words appear in the document in the order given.
 * The length of an interval is the number of words in that interval.
 */
public class DocumentLength {
    public static int intervalLength(String[] document, String[] query) {
        int queryCounter = 0;
        int lengthCounter = Integer.MAX_VALUE;
        int start = -1;
        int j = 0;
        while (j < document.length) {
            String d = document[j];
            if (d.equals(query[queryCounter])) {
                if (queryCounter == 0)
                    start = j;
                queryCounter++;
            }
            if (queryCounter == query.length) {
                // System.out.println("start " + start);
                // System.out.println("query.length " + j);
                lengthCounter = Math.min(lengthCounter, j - start + 1);
                queryCounter = 0;
                j = start;//
            }
            j++;
        }
        return lengthCounter;
    }

    public static int intervalLength2(String[] document, String[] query) {
        int queryCounter = 0;
        int shortestLength = Integer.MAX_VALUE;
        int start = -1;

        for (int j = 0; j < document.length; j++) {
            String d = document[j];
            if (d.equals(query[queryCounter])) {
                if (queryCounter == 0) {
                    start = j;
                }
                queryCounter++;

                if (queryCounter == query.length) {
                    int length = j - start + 1;
                    shortestLength = Math.min(shortestLength, length);
                    // Reset queryCounter to look for overlapping intervals
                    j = start; // Go back to the position after the previous start
                    queryCounter = 0;
                    start = -1;
                }
            }
        }

        // If shortestLength is still Integer.MAX_VALUE, it means no valid interval was found
        if (shortestLength == Integer.MAX_VALUE) {
            return -1;
        }
        return shortestLength;
    }

    public static void main(String[] args) {
        String[] document = {
                "this", "sample", "this", "is", "a", "sample", "this", "annoying", "and",
                "is", "sample", "example"
        };
        String[] words = { "this", "is", "sample" };

        int length = intervalLength(document, words);
        int length2 = intervalLength2(document, words);

        System.out.println("length = " + length);
        System.out.println("length2 = " + length2);
    }
}
