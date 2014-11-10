package org.zeroturnaround.jf.classloaders.ex4;

/**
 * Lets show
 * * <init> - default constructors
 * * Show super
 * * Variable copying to constructors
 * * final field initialization
 * * private constructor
 * * this()
 * * forward init references
 * * instance initializer
 * * anything else?
 */
public class Constructors extends BaseClass {
  // final int foo;
  int foo;
  //int bar = 40;
  
  // private Constructors() {}

  // public Constructors() {
  // this.foo = 42;
  // }

  // public Constructors(int a) {
  // //this();
  // }

  // public Constructors(int foo) {
  // this.foo = foo;
  // }

  public static void main(String[] args) {
    // Constructors cons = new Constructors();
    // System.out.println(cons.foo);
  }
}
