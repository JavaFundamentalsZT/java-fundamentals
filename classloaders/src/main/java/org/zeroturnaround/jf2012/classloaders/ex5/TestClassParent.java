package org.zeroturnaround.jf2012.classloaders.ex5;

public class TestClassParent {
  // instance initializer
  {
    System.out.println("Parent Instance init");
  }

  // static initializer
  // private static SecondTestClass secondClass = new SecondTestClass();
  static {
    System.out.println("Parent Static init");
  }

  // bunch of constructors
  public TestClassParent() {
    System.out.println("Parent Constructor Empty");
  }

  public TestClassParent(String param) {
    System.out.println("Parent Constructor String");
  }

}
