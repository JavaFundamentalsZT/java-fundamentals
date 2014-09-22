package datagram;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class TestPayloadSize {

  public static void main(String[] args) throws Exception {
    testByteBuffer('I', Long.MAX_VALUE);
    testObjectOutputStream('I', Long.MAX_VALUE);
  }

  private static void testByteBuffer(char type, long value) {
    ByteBuffer buf = ByteBuffer.allocate(48);
    buf.put((byte)type);
    buf.putLong(value);
    buf.flip();
    System.out.println("result buf: " + buf.remaining() + " bytes");
    ByteBuffer buf2 = ByteBuffer.wrap(buf.array());
    System.out.println("char equals: " + (buf2.get() == type));
    System.out.println("long equals: " + (buf2.getLong() == value));
  }

  private static void testObjectOutputStream(char type, long value) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeByte(type);
    oos.writeLong(value);
    oos.flush();
    System.out.println("result oos: " + bos.toByteArray().length + " bytes");
  }

}
