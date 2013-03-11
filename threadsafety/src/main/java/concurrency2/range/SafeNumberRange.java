package concurrency2.range;


class SafeNumberRange {
  Integer lower = 0;
  Integer upper = 0;
  synchronized void setLower(int i) {
    if (i > upper)
      throw new IllegalArgumentException();
    lower = i;
  }
  synchronized void setUpper(int i) {
    if (i < lower)
      throw new IllegalArgumentException();
    upper = i;
  }
  synchronized boolean isInRange(int i) {
    return (i >= lower && i <= upper);
  }
}
