package org.zeroturnaround.jf2012.concurrency.counter;

class Repeat implements Runnable {

  private final Runnable task;
  private final int times;

  public Repeat(Runnable task, int times) {
    this.task = task;
    this.times = times;
  }

  @Override
  public void run() {
    for (int i = 0; i < times; i++)
      task.run();
  }

}