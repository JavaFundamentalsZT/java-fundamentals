package util;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Thread and its stack trace elements.
 *
 * @author Rein Raudjärv
 */
public class StackTrace {

  public final Thread thread;

  public final StackTraceElement[] trace;

  public StackTrace(Thread thread, StackTraceElement[] trace) {
    this.thread = thread;
    this.trace = trace;
  }

  /**
   * @return class name of the Runnable or Thread of this thread (maybe <code>null</code>).
   */
  public String getMainClassName() {
    if (trace.length == 0)
      return null; // Empty
    StackTraceElement last = trace[trace.length - 1];
    if (last.getClassName().equals("java.lang.Thread")) {
      if (trace.length == 1)
        return null; // No more elements
      last = trace[trace.length - 2];
    }
    return last.getClassName();
  }

  /**
   * @return <code>true</code> if the stack trace contains a given method.
   */
  public boolean contains(String klass, String method) {
    for (int i = 0; i < trace.length; i++) {
      StackTraceElement e = trace[i];
      if (klass.equals(e.getClassName())
          && method.equals(e.getMethodName()))
        return true;
    }
    return false;
  }

  public String toString() {
    StringWriter sw = new StringWriter();
    PrintWriter out = new PrintWriter(sw);
    print(out);
    out.flush();
    return sw.getBuffer().toString();
  }

  private void print(PrintWriter s) {
    s.println();
    if (thread.isDaemon())
      s.print("daemon ");
    s.println(thread);
    for (int i = 0; i < trace.length; i++)
      s.println("\tat " + trace[i]);
  }

  public static String toStringAll() {
    return toString(Thread.getAllStackTraces());
  }

  public static String toString(Map traces) {
    StringWriter sw = new StringWriter();
    PrintWriter out = new PrintWriter(sw);
    out.println();
    out.println("Thread dump:");
    for (Iterator it = traces.entrySet().iterator(); it.hasNext();) {
      Entry entry = (Entry) it.next();
      new StackTrace((Thread) entry.getKey(), (StackTraceElement[]) entry.getValue()).print(out);
    }
    out.flush();
    return sw.getBuffer().toString();
  }

}