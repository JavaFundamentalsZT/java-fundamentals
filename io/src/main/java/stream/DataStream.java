package stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataStream {

  public static void main(String[] args) throws IOException {
    Path file = Files.createTempFile("sample", ".data");
    System.out.println(file);

    System.out.println("Byte: " + 0xA2);
//    System.out.println("Byte:" + Byte.toUnsignedInt(0xA2));

    try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(file))) {
//      out.writeByte(0xA2);
      out.writeByte(42);
      out.writeInt(42);
      out.writeUTF("Hello");
    }

    try (DataInputStream in = new DataInputStream(Files.newInputStream(file))) {
//      int b = in.readByte();
      int b = in.readUnsignedByte();
      System.out.println("Read byte: " + b);
      System.out.println("Read byte: " + Integer.toUnsignedString(b));
      System.out.println("Read byte: " + Byte.toUnsignedInt((byte) b));
      int i = in.readInt();
      System.out.println("Read int: " + i);
      String s = in.readUTF();
      System.out.println("Read String: " + s);
    }

  }

}
