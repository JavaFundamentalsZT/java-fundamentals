package org.zeroturnaround.jf2012.concurrency.promise;

public class UnexpectedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UnexpectedException(Throwable cause) {
    super(cause);
  }

}