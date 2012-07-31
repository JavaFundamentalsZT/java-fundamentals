package org.zeroturnaround.jf2012.classloaders;

public class MyClassLoader extends ClassLoader {
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    System.out.println("loadClass " + name);
    return super.loadClass(name);
  }
}
