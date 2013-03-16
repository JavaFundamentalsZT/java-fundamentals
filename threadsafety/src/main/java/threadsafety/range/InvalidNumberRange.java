package threadsafety.range;

import java.util.concurrent.atomic.AtomicInteger;

class InvalidNumberRange {
  AtomicInteger lower = new AtomicInteger(0);
  AtomicInteger upper = new AtomicInteger(0);
  void setLower(int i) {
    if (i > upper.get())
      throw new IllegalArgumentException();
    lower.set(i);
  }
  void setUpper(int i) {
    if (i < lower.get())
      throw new IllegalArgumentException();
    upper.set(i);
  }
  boolean isInRange(int i) {
    return (i >= lower.get() && i <= upper.get());
  }
}
