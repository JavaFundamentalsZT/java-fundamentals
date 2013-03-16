package threadsafety.philosophers.retry;



public class Main {

  public static void main(String[] args) {
    Table table = new Table();

    Philosopher[] philosophers = new Philosopher[Table.SIZE];
    for (int i = 0; i < philosophers.length; i++) {
      String name = "Philosopher " + (i+1);
      philosophers[i] = new Philosopher(name, new Location(table, i));
    }

    for (int i = 0; i < philosophers.length; i++)
      philosophers[i].start();
  }

}
