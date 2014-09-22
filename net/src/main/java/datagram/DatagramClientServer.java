package datagram;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatagramClientServer {

  public static class DatagramServer implements Runnable, Closeable {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DatagramSocket srv;
    private final Thread serverThread;
    private boolean isWaitingForMessages;
    private volatile boolean isClosing = false;

    public DatagramServer(int port) throws IOException {
      this.srv = new DatagramSocket(port);
      this.serverThread = new Thread(this, "acceptor");
      this.serverThread.setDaemon(false);
      this.serverThread.start();
      log.info("Server message listener started on port {}!", port);
    }

    @Override
    public void run() {
      while (!isClosing && !Thread.currentThread().isInterrupted()) {
        this.isWaitingForMessages = true;
        log.info("Waiting for packets");
        try {
          DatagramPacket requestPacket = makeEmptyDatagramPacket();
          srv.receive(requestPacket);
          log.info("Received packet from {}:{}", requestPacket.getAddress(), requestPacket.getPort());
          new DatagramServerWorker(srv, requestPacket);
          log.info("Done with packet from {}:{}", requestPacket.getAddress(), requestPacket.getPort());
        }
        catch (IOException e) {
          if (!isClosing) {
            log.warn("error while handling packet", e);
          } else {
            log.trace("error while handling packet during shutdown", e);
          }
        }
      }
    }

    @Override
    public void close() throws IOException {
      this.isClosing = true;
      if (serverThread != null) serverThread.interrupt();
      if (srv != null) {
        srv.close();
      }
    }

  }

  public static class DatagramServerWorker implements Runnable {

    private static int workerCounter = 0;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Thread workerThread;

    private final DatagramSocket srv;
    private final DatagramPacket requestPacket;

    public DatagramServerWorker(DatagramSocket srv, DatagramPacket requestPacket) {
      this.srv = srv;
      this.requestPacket = requestPacket;
      int workerNumber = ++workerCounter;
      this.workerThread = new Thread(this, "worker-" + workerNumber);
      this.workerThread.setDaemon(false);
      this.workerThread.start();
      log.info("Server worker #{} started to process message from {}:{}!",
          workerNumber, requestPacket.getAddress(), requestPacket.getPort());
    }

    @Override
    public void run() {
      try {
        SumMessage request = new SumMessage(requestPacket.getData());
        log.info("Processing message {}", request);
        long result = request.number * request.number;
        //sending response
        SumMessage response = new SumMessage(result);
        DatagramPacket responsePacket = makeDatagramPacket(
            requestPacket.getAddress(), requestPacket.getPort(), response);
        srv.send(responsePacket);
        log.info("Sent response message {}", response);
      } catch (Exception e) {
        log.error("error while handling packet", e);
      }
    }

  }

  public static void main(String[] args) {
    Logger log = LoggerFactory.getLogger("main");
    int port = 8099;
    List<Closeable> closeables = new ArrayList<>();
    try {
      try (DatagramServer srv = new DatagramServer(port)) {
        while (!srv.isWaitingForMessages) {Thread.sleep(100);}
        DatagramClient cl1 = new DatagramClient("localhost", port, 1000, 1,2,3,4,5,6,7,8,9,10);
        closeables.add(cl1);
        DatagramClient cl2 = new DatagramClient("localhost", port, 1000, 11,12,13,14,15);
        closeables.add(cl2);
        cl1.join();
        cl2.join();
      }
    }
    catch (Exception e) {
      log.error("", e);
    } finally {
      close(log, closeables);
    }
  }

  private static void close(Logger log, List<Closeable> closeables) {
    for (Closeable closeable : closeables) {
      try {
        closeable.close();
      }
      catch (IOException e) {
        log.debug("error while closing", e);
      }
    }
  }

  public static class DatagramClient implements Runnable, Closeable {

    private static int clientCounter = 0;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String host;
    private final int port;

    private final long[] numbers;
    private final long sleepBetweenNumbers;

    private final Thread clientThread;

    private volatile DatagramSocket s;

    private volatile boolean isClosing = false;

    public DatagramClient(String host, int port, long sleepBetweenNumbers, long... numbers) throws IOException {
      this.host = host;
      this.port = port;
      this.sleepBetweenNumbers = sleepBetweenNumbers;
      this.numbers = numbers;
      this.clientThread = new Thread(this, "client-" + (++clientCounter));
      this.clientThread.setDaemon(false);
      this.clientThread.start();
    }

    @Override
    public void run() {
      try {
        //open socket
        log.info("Client opening socket");
        this.s = new DatagramSocket();
        log.info("Client socket opened on {}:{}", s.getLocalAddress(), s.getLocalPort());
        //send and receive messages
        for (long number : numbers) {
          if (sleepBetweenNumbers > 0) {
            log.info("Sleeping for {}ms", sleepBetweenNumbers);
            Thread.sleep(sleepBetweenNumbers);
          }
          SumMessage request = new SumMessage(number);
          log.info("Client sending request {}", request);
          s.send(makeDatagramPacket(host, port, request));
          //waiting for response
          DatagramPacket responsePacket = makeEmptyDatagramPacket();
          s.receive(responsePacket);
          SumMessage response = new SumMessage(responsePacket.getData());
          log.info("Client received response message {}", response);
        }
        //clean up
        log.info("Client is done");
        close();
      }
      catch (IOException e) {
        if (!isClosing) {
          log.error("error in client", e);
        } else {
          log.trace("error in client while closing", e);
        }
      }
      catch (InterruptedException e) {
        if (!isClosing) {
          log.error("client was interrupted while running", e);
        }
      }
    }

    @Override
    public void close() throws IOException {
      this.isClosing = true;
      DatagramSocket s = this.s;
      this.s = null;
      if (s != null) {
        log.info("Client disconnecting on {}:{}", s.getLocalAddress(), s.getLocalPort());
        s.close();
      }
    }

    public final void join() throws InterruptedException {
      clientThread.join();
    }

  }

  private static class SumMessage {
    private long number;

    public SumMessage(long number) {
      this.number = number;
    }

    public SumMessage(byte[] bytes) {
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      this.number = buffer.getLong();
    }
    public byte[] toByteArray() {
      ByteBuffer buffer = ByteBuffer.wrap(makeByteArray());
      buffer.putLong(number);
      buffer.flip();
      byte[] result = new byte[buffer.remaining()];
      buffer.get(result);
      return result;
    }

    @Override
    public String toString() {
      return "SumMessage [number=" + number + "]";
    }

  }

  private static DatagramPacket makeEmptyDatagramPacket() {
    byte[] payload = makeByteArray();
    return new DatagramPacket(payload, payload.length);
  }

  private static DatagramPacket makeDatagramPacket(String host, int port, SumMessage msg) throws UnknownHostException {
    return makeDatagramPacket(InetAddress.getByName(host), port, msg);
  }

  private static DatagramPacket makeDatagramPacket(InetAddress address, int port, SumMessage msg) {
    byte[] bytes = msg.toByteArray();
    return new DatagramPacket(bytes, bytes.length, address, port);
  }

  private static byte[] makeByteArray() {
    return new byte[1024];
  }

}
