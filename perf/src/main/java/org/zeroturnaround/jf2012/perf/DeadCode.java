package org.zeroturnaround.jf2012.perf;

public class DeadCode {
	public static void main(String[] args) {
		System.out.println("Hello");

		for (int i = 0; i < 10000; i++) {
			myMethod();
		}
		System.out.println("World");
	}

	private static void myMethod() {
		// TODO Auto-generated method stub

	}
}
