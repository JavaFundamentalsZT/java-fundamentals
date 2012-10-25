package org.zeroturnaround.jf2012.classloaders.ex4;

public class TestClass {
  // instance initializer
  {
    System.out.println("Regular old body");
  }

  // static initializer
  static {
    System.out.println("Static block");
  }

  // bunch of constructors
  public TestClass() {
    System.out.println("Constructor Empty");
  }

  public TestClass(String param) {
    System.out.println("Constructor String");
  }

}
