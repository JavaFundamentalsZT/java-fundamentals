package org.zeroturnaround.jf.classloaders.ex3;

public class MyClassLoaderMain4 {
  public static void main(String[] args) throws Exception {
    MyClassLoader2 myOtherClassLoader = new MyClassLoader2();
    Class myClass = myOtherClassLoader.loadClass("org.zeroturnaround.jf2012.classloaders.ex3.MyCustomClass");

    System.out.println(myClass.getClassLoader());
    // System.out.println(MyCustomClass.class.getClassLoader());

    // MyCustomInterface myObj = (MyCustomInterface) myClass.newInstance();
    // myObj.printSomething();
    //
    // System.out.println(myObj.getClass().getClassLoader());
  }
}
