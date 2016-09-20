package stream.print;

/**
 * Created by rein on 14/09/16.
 */
public class FormatExample {

  public static void main(String[] args) {
    int i = 2;
    double r = Math.sqrt(i);
    System.out.format("The square root of %d is %f.%n", i, r);

    String key = "foo";
    boolean value = true;
    System.out.format("%s: %b%n", key, value);
  }

}
