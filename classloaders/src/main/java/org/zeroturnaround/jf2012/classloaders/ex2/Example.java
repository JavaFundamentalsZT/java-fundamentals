package org.zeroturnaround.jf2012.classloaders.ex2;

public class Example {
  public static void main(String[] args) {
    ClassLoader cl = Example.class.getClassLoader();
    // System.out.println(cl);
    // System.out.println(ClassLoader.getSystemClassLoader());

    // System.out.println(cl.getParent());
    // System.out.println(cl.getClass().getClassLoader());

    // System.out.println(System.getProperty("java.ext.dirs"));
    // System.out.println(com.sun.nio.zipfs.ZipInfo.class.getClassLoader());
    // System.out.println(cl.getParent().getParent());
  }
}
