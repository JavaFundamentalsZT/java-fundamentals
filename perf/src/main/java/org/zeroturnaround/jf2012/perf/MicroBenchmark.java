package org.zeroturnaround.jf2012.perf;

import java.util.Random;

public class MicroBenchmark {
	private static final Random random = new Random();

	public static void main(String[] args) {
		long timeNow = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			for (int j = 0; j < 10000000; j++) {
			}
		}
		System.out.println(System.currentTimeMillis() - timeNow);
	}

}
