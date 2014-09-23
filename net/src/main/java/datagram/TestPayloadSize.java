package datagram;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class TestPayloadSize {

  public static void main(String[] args) throws Exception {
    testByteBuffer(Long.MAX_VALUE);
    testObjectOutputStream(Long.MAX_VALUE);
  }

  private static void testByteBuffer(long value) {
    ByteBuffer buf = ByteBuffer.allocate(48);
    buf.putLong(value);
    buf.flip();
    System.out.println("result buf: " + buf.remaining() + " bytes");
    ByteBuffer buf2 = ByteBuffer.wrap(buf.array());
    System.out.println("long equals: " + (buf2.getLong() == value));
  }

  private static void testObjectOutputStream(long value) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeLong(value);
    oos.flush();
    System.out.println("result oos: " + bos.toByteArray().length + " bytes");
  }

}
