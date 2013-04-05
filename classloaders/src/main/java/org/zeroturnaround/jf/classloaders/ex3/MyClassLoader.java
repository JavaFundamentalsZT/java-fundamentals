package org.zeroturnaround.jf.classloaders.ex3;

import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader {
  public MyClassLoader(URL[] urls, ClassLoader cl) {
    super(urls, cl);
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    System.out.println("loadClass " + name);
    return super.loadClass(name);
  }
  
  @Override
  public String toString() {
    return "Custom ClassLoader";
  }
}
