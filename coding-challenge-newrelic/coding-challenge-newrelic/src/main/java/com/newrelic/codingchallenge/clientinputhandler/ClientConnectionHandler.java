package com.newrelic.codingchallenge.clientinputhandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import com.newrelic.codingchallenge.ServerStart;

/**
 * this class maintains list of all active client connections
 * to the application.
 * 
 * Switches the value of an atomic boolean between true/false
 * for the purpose of maintaining max five connections to the 
 * server application. 
 */
public class ClientConnectionHandler {

  private static List<Socket> clientSocketPool = new ArrayList<>();
  public static AtomicBoolean acceptMoreclients = new AtomicBoolean(true);

  private ClientConnectionHandler() {
  }

  public static void addSocket(Socket s) {
    clientSocketPool.add(s);
    if (clientSocketPool.size() >= ServerStart.maxClientConnectionAllowed) {
      acceptMoreclients.set(false);
    }
  }

  public static void removeSocket(Socket s) {
    clientSocketPool.remove(s);
    if (clientSocketPool.size() < ServerStart.maxClientConnectionAllowed) {
      acceptMoreclients.set(true);
    }
  }
  
  public static void terminateAllClientConnections() {

    clientSocketPool.forEach(s -> {
      try {
        s.close();
        System.out.println(
            "Connection closed for client adrdesss: " + s.getInetAddress().getHostAddress());
      } catch (IOException e) {
      }
    });
    clientSocketPool.clear();
    System.out.println("Client connections terminated");
  }
}
