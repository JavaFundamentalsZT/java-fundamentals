package org.zeroturnaround.jf2012.concurrency.counter;

public enum ConcurrentExecutorType {

  Barrier {
    @Override
    ConcurrentExecutor create() { return new BarrierExecutor(); }
  },
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