package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.Arrays;

public class Main {

  private static enum ConcurrentExecutorType {

    Countdown {
      @Override
      ConcurrentExecutor create() { return new CountdownExecutor(); }
    },
    CreateAndStart {
      @Override
      ConcurrentExecutor create() { return new CreateAndStartExecutor(); }
    },
    CreateThenStart {
      @Override
      ConcurrentExecutor create() { return new CreateThenStartExecutor(); }
    };

    abstract ConcurrentExecutor create();

  }

  private static enum TaskType {

    UnsafeCounter {
      @Override
      Runnable create() { return new UnsafeCounterTask(); }
    },
    SyncCounter {
      @Override
      Runnable create() { return new SyncCounterTask(); }
    },
    AtomicCounter {
      @Override
      Runnable create() { return new AtomicCounterTask(); }
    };

    abstract Runnable create();

  }

  private static final int DEFAULT_NO_THREADS = 10;

  private static final int DEFAULT_NO_TIMES = 1000;

  public static void main(String[] args) throws Exception {
    ConcurrentExecutorType executor = ConcurrentExecutorType.Countdown;
    TaskType task = TaskType.UnsafeCounter;
    int threads = DEFAULT_NO_THREADS;
    int times = DEFAULT_NO_TIMES;

    try {
      int i = 0;
      if (args.length > i) {
        executor = ConcurrentExecutorType.valueOf(args[i]);
        i++;
      }
      if (args.length > i) {
        task = TaskType.valueOf(args[i]);
        i++;
      }
      if (args.length > i) {
        threads = Integer.parseInt(args[i]);
        i++;
      }
      if (args.length > i) {
        threads = Integer.parseInt(args[i]);
        i++;
      }
    }
    catch (IllegalArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
      System.out.println();
      System.out.println("Args usages: [<executor> [<task> [<no of threads> [<no of times>]]]]");
      System.out.println();
      System.out.format("Available executors: %s%n", Arrays.toString(ConcurrentExecutorType.values()));
      System.out.format("Available tasks: %s%n", Arrays.toString(TaskType.values()));
      System.out.println();
      return;
    }

    System.out.format("Using executor: %s%n", executor);
    System.out.format("Using task: %s%n", task);
    System.out.format("No of threads: %d%n", threads);
    System.out.format("No of times: %d%n", times);
    execute(executor.create(), task.create(), threads, times);
  }

  private static void execute(ConcurrentExecutor executor, Runnable task, int threads, int times) throws Exception {
    Repeat repeat = new Repeat(task, times);
    long start = System.currentTimeMillis();
    executor.invoke(repeat, times);
    long time = System.currentTimeMillis() - start;
    System.out.format("%d x %d => %s%n", threads, times, task);
    System.out.format("Time taken: %d ms%n", time);
  }

}
