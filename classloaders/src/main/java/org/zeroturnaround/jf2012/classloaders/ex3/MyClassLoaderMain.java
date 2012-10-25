package org.zeroturnaround.jf2012.classloaders.ex3;

import java.lang.reflect.Method;
import java.net.URL;

public class MyClassLoaderMain {

  public static void main(String[] args) throws Exception {
    URL myClasses = new URL("file:/Users/toomasr/projects/java-fundamentals-course/classloaders/custom/classes/");
    MyClassLoader myClassLoader = new MyClassLoader(new URL[] { myClasses }, ClassLoader.getSystemClassLoader());
    
    Class myClass = myClassLoader.loadClass("org.zeroturnaround.jf2012.classloaders.ex3.MyCustomClass");

    Object myObj = myClass.newInstance();
    Method m = myObj.getClass().getMethod("printSomething", null);
    m.invoke(myObj, null);

    System.out.println(myObj);
  }
}
