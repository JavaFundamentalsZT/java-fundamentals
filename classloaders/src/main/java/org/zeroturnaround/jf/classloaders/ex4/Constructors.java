package org.zeroturnaround.jf.classloaders.ex4;

/**
 * Lets show
 *  * <init>
 *  * Variable copying to constructors
 *  * final field initialization
 *  * private constructor
 *  * this()
 *  * forward init references
 *  * anything else?
 */
public class Constructors extends BaseClass {
  // final int foo;
  int foo;

  public Constructors() {
    this.foo = 42;
  }
  
  // public Constructors(int foo) {
  // this.foo = foo;
  // }

  public static void main(String[] args) {
    // Constructors cons = new Constructors();
    // System.out.println(cons.foo);
  }
}
