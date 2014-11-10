package org.zeroturnaround.jf.classloaders.ex2;

public class Example {
  public static void main(String[] args) {
    ClassLoader cl = Example.class.getClassLoader();
    System.out.println("Example class's classloader " + cl);

    System.out.println("Classloader's classloader " + cl.getClass().getClassLoader());
    System.out.println("Parent class loader " + cl.getParent());

    System.out.println(System.getProperty("java.ext.dirs"));
    System.out.println(com.sun.nio.zipfs.ZipInfo.class.getClassLoader());
    
    System.out.println(cl.getParent().getParent());

    System.out.println("System classloader " + ClassLoader.getSystemClassLoader());
  }
}
