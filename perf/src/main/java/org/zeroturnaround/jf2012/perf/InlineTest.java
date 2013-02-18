package org.zeroturnaround.jf2012.perf;

public class InlineTest {
	private static final boolean debug = false;

	private void baz() {
		if (debug) {
			String a = foo();
		}
	}

	private String foo() {
		return bar();
	}

	private String bar() {
		return "abc";
	}
}
