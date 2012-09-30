package org.zeroturnaround.jf2012.concurrency.counter.executor;

public enum ConcurrentExecutorType {

  Barrier {
    @Override
    public ConcurrentExecutor create() { return new BarrierExecutor(); }
  },
  Countdown {
    @Override
    public ConcurrentExecutor create() { return new CountdownExecutor(); }
  },
  CreateAndStart {
    @Override
    public ConcurrentExecutor create() { return new CreateAndStartExecutor(); }
  },
  CreateThenStart {
    @Override
    public ConcurrentExecutor create() { return new CreateThenStartExecutor(); }
  };

  public abstract ConcurrentExecutor create();

}