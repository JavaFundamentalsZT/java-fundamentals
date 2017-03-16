import java.util.Locale;
import java.util.TreeMap;

/**
 * Created by rein on 14/09/16.
 */
public class Turkish {

  public static void main(String[] args) {
    test("i", Locale.ENGLISH);
    test("I", Locale.ENGLISH);
//    Locale turkey = new Locale("tr-TR");
    Locale turkey = new Locale("tr", "TR");
    test("i", turkey);
    test("I", turkey);
  }

  private static void test(String input, Locale locale) {
    String upper = input.toUpperCase(locale);
    String lower = input.toLowerCase(locale);
    System.out.format("%s\tUPPER: %s (%b)\tlower: %s (%b)\t%s%n", input, upper, upper.equals(input), lower, lower.equals(input), locale);
  }

}
