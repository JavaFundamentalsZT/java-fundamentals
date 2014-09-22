package slides;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ExamplesOnSlides {

  public static void main(String[] args) throws Exception {
    tcpServer();
  }

public static void openURL() throws Exception {
URL url = new URL("http://www.google.com");

try (InputStream in = url.openStream()) {
  //read stuff (check the last lecture)
}
}

public static void openSocket() throws Exception {
try (
  Socket s = new Socket("google.com", 80);
  InputStream in = s.getInputStream();
  OutputStream out = s.getOutputStream();
) {
  readAndWriteStuff(in, out);  
}
}

private static void readAndWriteStuff(InputStream in, OutputStream out) {
}

public static void tcpServer() throws Exception {
try (ServerSocket srv = new ServerSocket(80)) {
  while (true) {
    Socket s = srv.accept(); //this will block
    readAndWriteStuff(s);
  }
}
}

private static void readAndWriteStuff(Socket s) {
}

public void threadPool(Socket s) throws InterruptedException {
Socket socket = null;
int NR_THREADS = 50;
ExecutorService pool = 
Executors.newFixedThreadPool(NR_THREADS);
//...
Runnable command = new ClientHandler(socket);
pool.execute(command);
//...
pool.shutdown();
for (;;) {  
  pool.awaitTermination(1, TimeUnit.MINUTES);
  System.out.println("Still waiting");  
}
}

public class ClientHandler implements Runnable {
  
  private final Socket socket;
  
  public ClientHandler (Socket socket) {
    this.socket = socket;
  }  
  public void run() {
    //do stuff
  }
}

public void sendingDatagram() throws Exception {
byte[] buf = "hello".getBytes("UTF-8");

try (DatagramSocket sock = new DatagramSocket()) {      
  DatagramPacket message = new DatagramPacket(
      buf, 
      buf.length, 
      InetAddress.getByName("localhost"),
      6666);

  sock.send(message);
}

}

public void readingDatagram() throws Exception {
try (DatagramSocket sock = new DatagramSocket(6666)) {
  byte[] buf = new byte[8];
  DatagramPacket message = 
      new DatagramPacket(buf, buf.length);
  sock.receive(message);
  
  System.out.println(Arrays.toString(buf));
    //[104, 101, 108, 108, 111, 0, 0, 0]
}
  
}

public void readingDatagram2() throws Exception {
try (DatagramSocket sock = new DatagramSocket(6666)) {
  byte[] buf = new byte[8];
  DatagramPacket message = 
      new DatagramPacket(buf, buf.length);
  sock.receive(message);
  
  byte[] result = new byte[message.getLength()];
  System.arraycopy(buf,0,result,0,result.length);

  System.out.println(Arrays.toString(result));
  //[104, 101, 108, 108, 111]
}
}

public void secureSockets() throws Exception {
SSLSocketFactory factory = 
  (SSLSocketFactory)SSLSocketFactory.getDefault();

Socket sock = factory.createSocket("www.google.com", 443);
readAndWriteStuff(sock);
}

public void SocketChannel() throws Exception {
try (ServerSocketChannel server = 
  ServerSocketChannel.open()
) {
  server.configureBlocking(false);
  server.socket().bind(new InetSocketAddress(8888));
  
  Selector selector = Selector.open();
  server.register(selector, SelectionKey.OP_ACCEPT);
  //OP_CONNECT
  //OP_READ
  //OP_WRITE
}
 
}

public void SocketChannelLoop(Selector selector) throws Exception {
while (true) {
  selector.select();
  Set<SelectionKey> keys = selector.selectedKeys();
  
  for (SelectionKey key : keys) {
    if (key.isAcceptable())
      handleAccept(key);
    else if (key.isReadable())
      handleRead(key);

    keys.remove(key);
  }
}
}

private void handleAccept(SelectionKey key) throws Exception {
ServerSocketChannel srv = 
  (ServerSocketChannel) key.channel();

SocketChannel client = srv.accept();
client.configureBlocking(false);

client.register(
  key.selector(), SelectionKey.OP_READ);
}

private void handleRead(SelectionKey key) throws Exception {
SocketChannel client = 
  (SocketChannel) key.channel();

ByteBuffer buf = ByteBuffer.allocate(10);
client.read(buf);    
buf.flip();    

CharsetDecoder decoder =
  Charset.forName("UTF-8").newDecoder();

String msg = decoder.decode(buf).toString();
}

public void URLs() throws Exception {
URL url = new URL("http://www.google.com");
URL relative = new URL(url, "page?q=asdf");
URL pieces = new URL(
  "https", "google.com", 8443, "/page?q=asdf");

URL file = new URL("file:/C:/Windows");

URL custom = new URL("custom:/doStuff");

String use = "" + url + relative + pieces + file + custom;

URLConnection connection;

connection = url.openConnection();
connection.setRequestProperty("key", "value");
connection.getHeaderField("name");
connection.getInputStream();
}

public void httpSend(URL url) throws Exception {
HttpURLConnection conn = 
  (HttpURLConnection) url.openConnection();
conn.setRequestMethod("POST");
conn.setDoOutput(true);

try (
  OutputStreamWriter out =
  new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
) {
  out.write("Hello World!");
  out.flush();
}
}

private static class CustomUrlConnection extends URLConnection {

  protected CustomUrlConnection(URL url) {
    super(url);
  }

  @Override
  public void connect() throws IOException {
  }

  public void petTheKitty(boolean b) {
    // TODO Auto-generated method stub
    
  }
  
}

public void customProtocol() throws Exception {
URL url = new URL("custom:/doStuff");
url.openStream();

CustomUrlConnection conn = 
  (CustomUrlConnection) url.openConnection();

conn.petTheKitty(true);
}

private static class MyMessageHandler extends AbstractHandler {

public void handle(
    String target,
    Request baseRequest,
    HttpServletRequest request, 
    HttpServletResponse response)
        throws IOException {
  baseRequest.setHandled(true);
  response.getWriter().println("Hello world");
  response.setStatus(HttpServletResponse.SC_OK);
}

}

public void embedJetty() throws Exception {
final int PORT=8080;
Server jettySrv = new Server(PORT);
jettySrv.setHandler(new MyMessageHandler());
jettySrv.start();
jettySrv.join();
}

public void httpClient(byte[] content) throws Exception {
CloseableHttpClient client = HttpClientBuilder.create().build();
HttpPost request = new HttpPost("http://...");
request.setHeader("key", "value");
request.setEntity(new ByteArrayEntity(content));

HttpResponse response = client.execute(request);
EntityUtils.toByteArray(response.getEntity());
Header[] headers = response.getHeaders("headerName");
String.valueOf(headers);
}

private class Salute {
}
private class ComplexGreeting {
  public ComplexGreeting(Salute input) {
  }
}

@Path("greeting")
public class GreetingService {

  @POST  
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public ComplexGreeting invoke(Salute input) {
    return new ComplexGreeting(input);
  }
  
}

public void fileChannel() throws Exception {
RandomAccessFile file =
    new RandomAccessFile("example.txt", "rw");
FileChannel inChannel = file.getChannel();
ByteBuffer buf = ByteBuffer.allocate(48);

int bytesRead = inChannel.read(buf);
while (bytesRead != -1) {
  System.out.println("Read " + bytesRead);
  buf.flip();

  while(buf.hasRemaining()){
      System.out.print((char) buf.get());
  }

  buf.clear();
  bytesRead = inChannel.read(buf);
}
file.close();
}

}
