package org.zeroturnaround.jf2012.classloaders.ex3;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyClassLoader2 extends ClassLoader {
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    String classFile = "custom/classes/" + name.replace(".", "/") + ".class";
    Path path = FileSystems.getDefault().getPath(classFile);

    if (!path.toFile().exists()) {
      return getParent().loadClass(name);
    }

    byte[] bytes = null;
    try {
      bytes = Files.readAllBytes(path);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    return defineClass(name, bytes, 0, bytes.length);
  }
}
