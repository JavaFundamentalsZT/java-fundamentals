package org.zeroturnaround.jf2012.classloaders.ex3;

import java.lang.reflect.Method;

public class MyClassLoaderMain {

  public static void main(String[] args) throws Exception {
    MyClassLoader myClassLoader = new MyClassLoader();
    Class myClass = myClassLoader.loadClass("org.zeroturnaround.jf2012.classloaders.ex3.MyCustomClass");

    Object myObj = myClass.newInstance();
    Method m = myObj.getClass().getMethod("printSomething", null);
    m.invoke(myObj, null);
    
    System.out.println(myObj);
  }

}
