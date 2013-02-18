package org.zeroturnaround.jf2012.perf;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class Crash {
	public static void main(String... args) throws Exception {
		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		Unsafe u = (Unsafe) f.get(null);
		u.putAddress(0, 0);
	}

}
