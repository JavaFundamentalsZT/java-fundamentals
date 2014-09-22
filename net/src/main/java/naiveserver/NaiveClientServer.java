package naiveserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NaiveClientServer {

  public NaiveClientServer(int port) throws IOException {
    try (ServerSocket srv = new ServerSocket(port)) {
      while (true) {
        Socket s = srv.accept();
        readAndWriteStuffOnServer(s);
      }
    }
  }

  private void readAndWriteStuffOnServer(Socket s) throws IOException {
    try (
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
      ) {
        readAndWriteStuffOnServer(in, out);
      }
  }

  private void readAndWriteStuffOnServer(InputStream in, OutputStream out) throws IOException {
    ObjectInputStream ois = new ObjectInputStream(in);
    long number = ois.readLong();
    System.out.format("server got %s", number);
    ObjectOutputStream oos = new ObjectOutputStream(out);
    long result = number * number;
    oos.writeLong(result);
    System.out.format("server wrote %s", result);
  }

  public static void main(String[] args) throws IOException {
    int port = 8099;
    new NaiveClientServer(port);
    new NaiveClient("localhost", port);
    System.out.println("Server started!");
  }

  public static class NaiveClient {

    public NaiveClient(String host, int port) throws IOException {
      try (
        Socket s = new Socket(host, port);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
      ) {
        readAndWriteStuffOnClient(in, out);  
      }
    }

    private void readAndWriteStuffOnClient(InputStream in, OutputStream out) throws IOException {
      ObjectOutputStream oos = new ObjectOutputStream(out);
      long number = Math.round(Math.random() * (double) 100);
      oos.writeLong(number);
      System.out.format("client wrote %s", number);
      ObjectInputStream ois = new ObjectInputStream(in);
      long result = ois.readLong();
      System.out.format("client got %s", result);
    }

  }

}
