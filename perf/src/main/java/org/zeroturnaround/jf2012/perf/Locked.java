package org.zeroturnaround.jf2012.perf;

public class Locked {
	public static void main(String[] args) {
		final Locked locked = new Locked();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				locked.myMethod();
			}
		};
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				locked.myMethod();
			}
		};
		
		t1.setName("DEMO-1");
		t1.start();
		t2.setName("DEMO-2");
		t2.start();
	}

	private synchronized void myMethod() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
