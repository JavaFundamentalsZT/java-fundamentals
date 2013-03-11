package concurrency2.range;

import javax.annotation.concurrent.NotThreadSafe;


@NotThreadSafe
public class SimpleNumberRange {

  // INVARIANT: lower <= upper
  private Integer lower = 0;
  private Integer upper = 0;

  public void setLower(int i) {
    // Warning -- unsafe check-then-act
    if (i > upper)
      throw new IllegalArgumentException("can't set lower to " + i + " > upper");
    lower = i;
  }

  public void setUpper(int i) {
    // Warning -- unsafe check-then-act
    if (i < lower)
      throw new IllegalArgumentException("can't set upper to " + i + " < lower");
    upper = i;
  }

  public boolean isInRange(int i) {
    return (i >= lower && i <= upper);
  }

}
