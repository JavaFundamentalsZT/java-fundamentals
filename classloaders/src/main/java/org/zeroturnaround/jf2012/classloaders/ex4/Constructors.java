package org.zeroturnaround.jf2012.classloaders.ex4;

public class Constructors extends BaseClass {
	final int foo;
	
	public Constructors() {
		this.foo = 42;
	}

	public static void main(String[] args) {
		Constructors cons = new Constructors();
		System.out.println(cons.foo);
	}
}
