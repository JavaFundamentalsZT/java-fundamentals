package org.zeroturnaround.jf.debugging;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class JVMCrash {
  public static void main(String... args) throws Exception {
    getUnsafe().getByte(0);
  }

  private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
    theUnsafe.setAccessible(true);
    return (Unsafe) theUnsafe.get(null);
  }
}
