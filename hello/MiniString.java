/* *****************************************************************************
 *  Name:              Amin Tazifor
 *  Coursera User ID:  123456
 *  Last modified:     July 11, 2021
 **************************************************************************** */

public class MiniString {
    private final char[] value;
    private final int offset;
    private final int count;

    public MiniString(char[] value, int offset, int count) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    MiniString(int offset, int count, char value[]) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    public MiniString substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex > count) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        if (beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException(endIndex - beginIndex);
        }
        return ((beginIndex == 0) && (endIndex == count)) ? this :
               new MiniString(offset + beginIndex, endIndex - beginIndex, value);
    }

    public static void main(String[] args) {

    }
}
