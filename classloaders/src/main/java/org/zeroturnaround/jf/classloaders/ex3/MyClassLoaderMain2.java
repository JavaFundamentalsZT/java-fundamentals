package org.zeroturnaround.jf.classloaders.ex3;

import java.lang.reflect.Method;

public class MyClassLoaderMain2 {
  public static void main(String[] args) throws Exception {
    MyClassLoader2 myOtherClassLoader = new MyClassLoader2();
    Class myClass = myOtherClassLoader.loadClass("org.zeroturnaround.jf2012.classloaders.ex3.MyCustomClass");

    Object myObj = myClass.newInstance();
    Method m = myObj.getClass().getMethod("printSomething", null);
    m.invoke(myObj, null);

    System.out.println(myObj.getClass().getClassLoader());
  }
}
