package org.zeroturnaround.jf2012.perf;

public class DebugMeMore {
	public static void main(String[] args) throws Exception {
		while (true) {
			myMethod();
			Thread.sleep(1000);
		}
	}

	private static void myMethod() {
		int a = 4;
		int b = 8;
		long c = 0;
		for (int i = 0; i < 5000; i++) {
			c = calculate(a, b) + i;
		}
		System.out.println(c);
	}

	private static long calculate(int a, int b) {
		long rtrn = 0;
		for (int i = 0; i < 10000; i++) {
			rtrn = a + b + i;
		}
		return rtrn;
	}
}
