package slides;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.IOUtils;
import org.zeroturnaround.exec.ProcessExecutor;

public class ExamplesOnSlidesProcess {

public static void main(String[] args) throws Exception {
  commonsExecWithTimeout();
}

public static void startChrome() throws IOException {
ProcessBuilder builder = new ProcessBuilder(
  "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "https://courses.cs.ut.ee/2014/javaFund/fall");
builder.start();
}

public static void startPs() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ps");
builder.start();
}

public static void startPsWithRedirectInherit() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ps");
builder.redirectOutput(Redirect.INHERIT);
builder.start();
}

public static void startPsWithInherit() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ps");
builder.inheritIO();
builder.start();
}

public static void startPsWithRedirectToFile() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ps");
File file = new File("out.txt");
builder.redirectOutput(Redirect.to(file));
builder.start();
}

public static void startPsWithCopyProcessOutput() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ps");
Process process = builder.start();
InputStream out = process.getInputStream();
IOUtils.copy(out, System.out);
}

public static void startLsWithCopyProcessError() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ls", "notfound");
Process process = builder.start();
InputStream err = process.getErrorStream();
IOUtils.copy(err, System.err);
}

public static void startLsWithCopyProcessOutputAndError() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ls", "notfound");
builder.redirectErrorStream(true);
Process process = builder.start();
InputStream out = process.getInputStream();
IOUtils.copy(out, System.out);
}

public static void startHeadWithCopyProcessOutputAndInput() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("head", "-n", "3");
Process p = builder.start();
//run in background
new Thread(() ->  copyStream(
  p.getInputStream(), System.out)).start();
IOUtils.copy(System.in, p.getOutputStream());
}

//we need this to get rid of the IOException
private static void copyStream(InputStream is, OutputStream os) {
  try {
    IOUtils.copy(is, os);
  }
  catch (IOException e) {
    throw new RuntimeException(e);
  }
}

public static void startHeadWithCopyProcessOutputAndInputWithFlush() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("head", "-n", "3");
Process p = builder.start();
//run in background
new Thread(() ->  copyStream(
  p.getInputStream(), System.out)).start();
new Thread(() ->  copyStreamWithFlush(
  System.in, p.getOutputStream(), p)).start();
try {
  while (p.isAlive()) {
    Thread.sleep(100);
  }
}
catch (InterruptedException e) {
  Thread.currentThread().interrupt();
}
//this will cause the System.in copier to close
System.in.close();
}

private static void copyStreamWithFlush(InputStream is, OutputStream os, Process p) {
  try {
int i;
while ((i = is.read()) != -1 && p.isAlive()) {
  os.write(i);
  os.flush();
}
  }
  catch (IOException e) {
    throw new RuntimeException(e);
  }
}

public static void startLsWithWorkingDirectory() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("ls");
builder.directory(
  new File("/"));
builder.redirectOutput(
  Redirect.INHERIT);
builder.start();
}

public static void startEnv() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("env");
builder.inheritIO();
Map<String, String> env =
  builder.environment();
env.put("foo", "bar");
env.put("PATH",
  "/myBin" + File.pathSeparator + env.get("PATH"));
builder.start();
}

// /Applications/Calculator.app/Contents/MacOS/Calculator
public static void startCalcWithPath() throws IOException {
ProcessBuilder builder =
  new ProcessBuilder("Calculator");
builder.inheritIO();
Map<String, String> env =
  builder.environment();
env.put("PATH",
  "/Applications/Calculator.app/Contents/MacOS" + File.pathSeparator + env.get("PATH"));
builder.start();
}

public static void startCalcWithWait() throws Exception {
ProcessBuilder builder =
  new ProcessBuilder(
    "/Applications/Calculator.app"
    + "/Contents/MacOS/Calculator");
Process process = builder.start();
process.waitFor();
}

public static void startCatWithExitCheck() throws Exception {
ProcessBuilder builder =
  new ProcessBuilder("cat", "test.txt");
builder.inheritIO();
Process process = builder.start();
int exitCode = process.waitFor();
//0 – success, 1 – file not found
if (exitCode != 0) {
  throw new IllegalStateException("Exit code: " + exitCode);
}
}

public static void startTrWithCloseStream() throws Exception {
ProcessBuilder builder = new ProcessBuilder(
  "tr", "-u", "[:lower:]", "[:upper:]");
builder.redirectOutput(Redirect.INHERIT);
Process process = builder.start();
PrintWriter in = new PrintWriter(process.getOutputStream(), true);
in.println("James"); in.println("Bond");
in.close();
}

public static void startPingWithDestroy() throws Exception {
ProcessBuilder builder = new ProcessBuilder(
  "ping", "ut.ee");
builder.redirectOutput(Redirect.INHERIT);
Process process = builder.start();
Thread.sleep(5000);
process.destroy();
}

public static void startPingWithDestroyInFinally() throws Exception {
ProcessBuilder builder = new ProcessBuilder(
  "ping", "ut.ee");
builder.redirectOutput(Redirect.INHERIT);
Process process = builder.start();
try {
  doStuff(process);
}
finally {
  process.destroy();
}
}

private static void doStuff(Process process) throws InterruptedException {
  Thread.sleep(5000);
}

public static void commonsExecWithTimeout() throws Exception {
CommandLine cmdLine = new CommandLine(
  "/Applications/Calculator.app"
  + "/Contents/MacOS/Calculator");
DefaultExecutor executor =
  new DefaultExecutor();
executor.setWatchdog(
  new ExecuteWatchdog(10*1000));
executor.execute(cmdLine);
}

public static void ztExecWithTimeout() throws Exception {
try {
  new ProcessExecutor().command(
      "/Applications/Calculator.app"
          + "/Contents/MacOS/Calculator")
        .timeout(10, TimeUnit.SECONDS).execute();
}
catch (TimeoutException e) {
  // process is automatically destroyed
}
}

}
