/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class EqualityTests {
    public static void main(String[] args) {
        double a = 0.0;
        double b = -0.0;
        System.out.println(String.format("%s == %s ? %s", a, b, a == b));
        System.out.println(String.format("Doule(%s).equals(Double(%s)) ? %s", a, b,
                                         Double.valueOf(a).equals(Double.valueOf(b))));

        double x = Double.NaN;
        double y = Double.NaN;
        System.out.println(String.format("%s == %s ? %s", x, y, x == y));
        System.out.println(String.format("Doule(%s).equals(Double(%s)) ? %s", x, y,
                                         Double.valueOf(x).equals(Double.valueOf(y))));
    }
}
