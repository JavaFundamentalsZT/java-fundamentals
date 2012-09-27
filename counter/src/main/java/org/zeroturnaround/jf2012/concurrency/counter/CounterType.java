package org.zeroturnaround.jf2012.concurrency.counter;

public enum CounterType {

  SimplePrimitive {
    @Override
    Counter create() { return new SimplePrimitiveCounter(); }
  },
  SimpleWrapper {
    @Override
    Counter create() { return new SimpleWrapperCounter(); }
  },
  VolatilePrimitive {
    @Override
    Counter create() { return new VolatilePrimitiveCounter(); }
  },
  VolatileWrapper {
    @Override
    Counter create() { return new VolatileWrapperCounter(); }
  },
  SyncPrimitive {
    @Override
    Counter create() { return new SyncPrimitiveCounter(); }
  },
  SyncWrapper {
    @Override
    Counter create() { return new SyncWrapperCounter(); }
  },
  Atomic {
    @Override
    Counter create() { return new AtomicCounter(); }
  };

  abstract Counter create();

}