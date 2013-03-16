package threadsafety.philosophers.unsafe;

import java.util.concurrent.locks.Lock;

public class Location {

  private final Table table;

  private final int leftIndex;

  private final int rightIndex;

  public Location(Table table, int index) {
    this.table = table;
    leftIndex = (Table.SIZE + index - 1) % Table.SIZE;
    rightIndex = (index + 1) % Table.SIZE;
  }

  public Object getLeft() {
    return table.forks[leftIndex];
  }

  public Object getRight() {
    return table.forks[rightIndex];
  }

}
