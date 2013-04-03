package org.zeroturnaround.jf2012.classloaders.ex3;

public class MyClassLoaderMain3 {
  public static void main(String[] args) throws Exception {
    MyClassLoader2 myOtherClassLoader = new MyClassLoader2();
    Class myClass = myOtherClassLoader.loadClass("org.zeroturnaround.jf2012.classloaders.ex3.MyCustomClass");

    System.out.println(myClass.getClassLoader());
    System.out.println(MyClassLoaderMain3.class.getClassLoader());

    // MyCustomClass myObj = (MyCustomClass) myClass.newInstance();
    // myObj.printSomething();
    //
    // System.out.println(myObj.getClass().getClassLoader());
  }
}
