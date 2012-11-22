package org.zeroturnaround.jf2012.perf;

import java.util.ArrayList;
import java.util.List;

public class OOM {
	private static final List<Long> letsTryToOom = new ArrayList<Long>();

	public static void main(String[] args) {
		for (long i = 0; i < Long.MAX_VALUE; i++) {
			letsTryToOom.add(i);
			if (i != 0 && i%10000000==0)
				System.out.println("10 mln added");
		}
	}
}
