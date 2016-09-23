package processbuilder.output;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class OutputReadExample {

  public static void main(String[] args) throws Exception {
    readOutput();
  }

  private static void byteArrayOutput() throws IOException, InterruptedException {
    Process process = new ProcessBuilder("ps").start();

    InputStream stdout = process.getInputStream();
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    IOUtils.copy(stdout, buffer);
    byte[] output = buffer.toByteArray();
    System.out.write(output);

    process.waitFor();
  }

  private static void bufferOutput() throws IOException, InterruptedException {
    Process process = new ProcessBuilder("ps").start();

    InputStream stdout = process.getInputStream();
    byte[] output = IOUtils.toByteArray(stdout);
    System.out.write(output);

    process.waitFor();
  }

  private static void readOutput() throws IOException, InterruptedException {
    Process process = new ProcessBuilder("ps").start();

    InputStream stdout = process.getInputStream();
    Charset cs = StandardCharsets.UTF_8;
    String output = IOUtils.toString(stdout, cs);
    System.out.println(output);

    process.waitFor();
  }

  private static void readOutputAndError() throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder("ps");
    pb.redirectErrorStream(true);
    Process process = pb.start();

    InputStream stdout = process.getInputStream();
    Charset cs = StandardCharsets.UTF_8;
    String output = IOUtils.toString(stdout, cs);
    System.out.println(output);

    process.waitFor();
  }

}
