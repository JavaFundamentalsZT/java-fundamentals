package org.zeroturnaround.jf2012.concurrency.counter;

public enum CounterType {

  Simple {
    @Override
    Counter create() { return new SimpleCounter(); }
  },
  Volatile {
    @Override
    Counter create() { return new VolatileCounter(); }
  },
  Sync {
    @Override
    Counter create() { return new SyncCounter(); }
  },
  Atomic {
    @Override
    Counter create() { return new AtomicCounter(); }
  };

  abstract Counter create();

}