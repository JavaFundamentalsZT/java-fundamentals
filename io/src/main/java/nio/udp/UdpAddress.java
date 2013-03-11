package nio.udp;

public class UdpAddress {

  static final String host;
  static final int receiverPort;
  static final Integer senderPort;
  
  static {
    {
      String hostStr = System.getProperty("host");
      if (hostStr == null)
        hostStr = "localhost";
      host = hostStr;
    }
    
    {
      String portStr = System.getProperty("port");
      if (portStr != null)
        receiverPort = Integer.parseInt(portStr);
      else
        receiverPort = 9999;
    }

    {
      String portStr = System.getProperty("senderPort");
      if (portStr != null)
        senderPort = Integer.parseInt(portStr);
      else
        senderPort = null;
    }
  }
  
}
