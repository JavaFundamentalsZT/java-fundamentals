package org.zeroturnaround.jf2012.perf;

public class ConstantFolding {

	private static final int a = 100;
	private static final int b = 200;

	public final void baz() {
		int c = a + b;
	}
}