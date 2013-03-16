package threadsafety.philosophers.retry;

import java.util.concurrent.TimeUnit;

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
    while (!tryEat());
  }
  private boolean tryEat() throws InterruptedException {
    if (!location.getLeft().tryLock(GET_FORK_LENGTH, TimeUnit.MILLISECONDS)) {
      System.out.format("%s couldn't get the left fork.%n", getName());
      return false;
    }
    try {
      if (!location.getRight().tryLock(GET_FORK_LENGTH, TimeUnit.MILLISECONDS)) {
        System.out.format("%s couldn't get the right fork.%n", getName());        
        return false;
      }
      try {
        System.out.format("%s eating...%n", getName());
        sleep(EAT_LENGTH);
      }
      finally {
        location.getRight().unlock();
      }
    }
    finally {
      location.getLeft().unlock();
    }
    return true;
  }

}
