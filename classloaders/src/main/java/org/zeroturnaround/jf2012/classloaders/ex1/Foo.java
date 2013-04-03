package org.zeroturnaround.jf2012.classloaders.ex1;

public class Foo {
  private String firstName;
  private String lastName;

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public static void main(String[] args) {
    Foo foo = new Foo();
  }
}