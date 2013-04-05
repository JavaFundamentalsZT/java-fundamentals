package org.zeroturnaround.jf.classloaders.ex5;

public class TestClass extends TestClassParent {
  // instance initializer
  {
    System.out.println("Instance init");
  }

  // static initializer
  // private static SecondTestClass secondClass = new SecondTestClass();
  static {
    System.out.println("Static init");
  }

  // bunch of constructors
  public TestClass() {
    System.out.println("Constructor Empty");
  }

  public TestClass(String param) {
    System.out.println("Constructor String");
  }

}
