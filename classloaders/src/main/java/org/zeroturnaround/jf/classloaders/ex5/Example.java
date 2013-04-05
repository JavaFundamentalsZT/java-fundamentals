package org.zeroturnaround.jf.classloaders.ex5;

public class Example {
  public static void main(String[] args) throws Exception {
    System.out.println(new TestClass());
    // System.out.println("New instance");
    // System.out.println(new TestClass("Hello"));
    // Class clazz = Class.forName("org.zeroturnaround.jf2012.classloaders.ex5.TestClass", false, Example.class.getClassLoader());
    // System.out.println(clazz);

    // TestClass testClass = (TestClass) clazz.newInstance();
  }
}
