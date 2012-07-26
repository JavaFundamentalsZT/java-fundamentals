package org.zeroturnaround.jf2012.classloaders;

public class Main {
  public static void main(String[] args) throws Exception {
    MyClassLoader myClassLoader = new MyClassLoader();
    Class runnableClass = myClassLoader.loadClass("org.zeroturnaround.jf2012.classloaders.SimpleLoaderRunnable");
    Thread thread = new Thread((Runnable) runnableClass.newInstance());
    thread.setContextClassLoader(myClassLoader);
    thread.start();
  }
}
