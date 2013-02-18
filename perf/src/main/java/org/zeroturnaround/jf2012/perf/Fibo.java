package org.zeroturnaround.jf2012.perf;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fibo {
	public static void main(String[] args) {
		// long result = fibo(55);
		// System.out.println(result);

		recursion(4500);
	}

	private static void recursion(int i) {
		Calendar cal = new GregorianCalendar();
		if (i == 0)
			return;
		recursion(i - 1);
	}

	private static long fibo(int i) {
		if (i <= 1)
			return i;
		return fibo(i - 1) + fibo(i - 2);
	}
}
