package threadsafety.philosophers.unsafe;



public class Table {

  static final int SIZE = 5;

  final Object[] forks;

  public Table() {
    forks = new Object[SIZE];
    for (int i = 0; i < SIZE; i++) {
      forks[i] = new Object();
    }
  }

}
