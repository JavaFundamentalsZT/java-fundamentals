package org.zeroturnaround.jf2012.perf;

public class MicroBenchmark2 {
	public static void doSomeStuff() {
		double uselessSum = 0;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				uselessSum += (double) i + (double) j;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		doSomeStuff();

		int nThreads = Integer.parseInt(args[0]);
		Thread[] threads = new Thread[nThreads];
		for (int i = 0; i < nThreads; i++)
			threads[i] = new Thread(new Runnable() {
				public void run() {
					doSomeStuff();
				}
			});
		long start = System.currentTimeMillis();
		for (int i = 0; i < threads.length; i++)
			threads[i].start();
		for (int i = 0; i < threads.length; i++)
			threads[i].join();
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + "ms");
	}
}
