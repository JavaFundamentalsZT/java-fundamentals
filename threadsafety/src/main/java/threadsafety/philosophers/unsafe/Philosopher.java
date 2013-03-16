package threadsafety.philosophers.unsafe;


public class Philosopher extends Thread {

  private static final int TIMES = 5;

  private static final long GET_FORK_LENGTH = 10;
  private static final long EAT_LENGTH = 10;

  private final Location location;

  public Philosopher(String name, Location location) {
    super(name);
    this.location = location;
  }

  @Override
  public void run() {
    System.out.format("%s started.%n", getName());
    try {
      for (int i = 0; i < TIMES; i++) {
        eat();
      }
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.format("%s finished.%n", getName());
  }

  private void eat() throws InterruptedException {
    synchronized (location.getLeft()) {
      synchronized (location.getRight()) {
        System.out.format("%s eating...%n", getName());
        sleep(EAT_LENGTH);
      }
    }
  }

}
