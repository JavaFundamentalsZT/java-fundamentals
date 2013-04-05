package org.zeroturnaround.jf.classloaders.ex4;

public class Init {
	boolean myBool;
	byte myByte;
	short myShort;
	int myInt;
	long myLong;
	char myChar;
	float myFloat;
	double myDouble;
	Object myObj;

	public static void main(String[] args) {
		Init init = new Init();
		System.out.println(init.myBool);
		System.out.println(init.myByte);
		System.out.println(init.myShort);
		System.out.println(init.myInt);
		System.out.println(init.myLong);
		System.out.println(init.myChar);
		System.out.println(init.myFloat);
		System.out.println(init.myDouble);
		System.out.println(init.myObj);

		init.foobar();
	}

	private void foobar() {
		//int foo;
		//System.out.println(foo);
	}
}
