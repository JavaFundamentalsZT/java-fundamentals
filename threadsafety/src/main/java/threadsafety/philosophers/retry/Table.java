package threadsafety.philosophers.retry;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Table {

  static final int SIZE = 5;

  final Lock[] forks;

  public Table() {
    forks = new Lock[SIZE];
    for (int i = 0; i < SIZE; i++) {
      forks[i] = new ReentrantLock();
    }
  }

}
