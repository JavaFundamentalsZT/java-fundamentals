package org.zeroturnaround.jf2012.perf;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Fibo {
	public static void main(String[] args) {
		//long result = fibo(80);
		//System.out.println(result);
		int result = recursion(9990);
		System.out.println(result);
	}

	private static int recursion(int i) {
		Calendar cal = new GregorianCalendar();
		if (i == 0)
			return 0;
		else
			return recursion(i-1);
	}

	private static long fibo(int i) {
		if (i <= 1)
			return i;
		return fibo(i - 1) + fibo(i - 2);
	}
}
