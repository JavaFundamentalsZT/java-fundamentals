package org.zeroturnaround.jf2012.concurrency.counter.counter;

public enum CounterType {

  SimplePrimitive {
    @Override
    public Counter create() { return new SimplePrimitiveCounter(); }
  },
  SimpleWrapper {
    @Override
    public Counter create() { return new SimpleWrapperCounter(); }
  },
  VolatilePrimitive {
    @Override
    public Counter create() { return new VolatilePrimitiveCounter(); }
  },
  VolatileWrapper {
    @Override
    public Counter create() { return new VolatileWrapperCounter(); }
  },
  SyncPrimitive {
    @Override
    public Counter create() { return new SyncPrimitiveCounter(); }
  },
  SyncWrapper {
    @Override
    public Counter create() { return new SyncWrapperCounter(); }
  },
  Atomic {
    @Override
    public Counter create() { return new AtomicCounter(); }
  };

  public abstract Counter create();

}