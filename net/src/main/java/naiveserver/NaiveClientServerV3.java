package naiveserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaiveClientServerV3 implements Runnable, AutoCloseable {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ServerSocket srv;
  private final Thread serverThread;
  private boolean isWaitingForConnections;

  public NaiveClientServerV3(int port) throws IOException {
    this.srv = new ServerSocket(port);
    this.serverThread = new Thread(this, "acceptor");
    this.serverThread.setDaemon(false);
    this.serverThread.start();
    log.info("Server started on port {}!", port);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      this.isWaitingForConnections = true;
      log.info("Waiting for new connection");
      try (Socket s = srv.accept()) {
        log.info("Accepted socket {}", s);
        readAndWriteStuffOnServer(s);
        log.info("Done with socket {}", s);
      }
      catch (IOException e) {
        log.warn("error while handling socket", e);
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
    log.info("server got {}", number);
    ObjectOutputStream oos = new ObjectOutputStream(out);
    long result = number * number;
    oos.writeLong(result);
    log.info("server wrote {}", result);
  }

  @Override
  public void close() throws Exception {
    if (serverThread != null) serverThread.interrupt();
    if (srv != null) srv.close();
  }

  public static void main(String[] args) {
    Logger log = LoggerFactory.getLogger("main");
    int port = 8099;
    try {
      try (NaiveClientServerV3 srv = new NaiveClientServerV3(port)) {
        while (!srv.isWaitingForConnections) {Thread.sleep(100);}
        new NaiveClient("localhost", port);
        new NaiveClient("localhost", port);
      }
    }
    catch (Exception e) {
      log.error("", e);
    }
  }

  public static class NaiveClient {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public NaiveClient(String host, int port) throws IOException {
      log.info("Client connecting to {}:{}", host, port);
      try (
        Socket s = new Socket(host, port);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
      ) {
        log.info("Client connected {} -> {}", s.getLocalPort(), s.getPort());
        readAndWriteStuffOnClient(in, out);  
      }
    }

    private void readAndWriteStuffOnClient(InputStream in, OutputStream out) throws IOException {
      ObjectOutputStream oos = new ObjectOutputStream(out);
      long number = Math.round(Math.random() * (double) 100);
      oos.writeLong(number);
      log.info("Client wrote {}", number);
      ObjectInputStream ois = new ObjectInputStream(in);
      long result = ois.readLong();
      log.info("Client got {}", result);
    }

  }

}
