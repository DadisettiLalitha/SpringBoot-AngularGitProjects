package com.newrelic.codingchallenge;

import java.net.InetAddress;
import java.net.ServerSocket;
import com.newrelic.codingchallenge.cache.Cache;
import com.newrelic.codingchallenge.clientinputhandler.ClientHandler;
import com.newrelic.codingchallenge.process.NumberProcessor;

/**
 * the server application thread.
 * this class is responsible for application termination, requesting 
 * for creation of application level threads.. 
 */
public class ServerStart {

  private static ServerStart app;
  private ServerSocket server;

  public static int maxClientConnectionAllowed;
  private int threadPoolSize;
  private ThreadPool threadPool;

  public ServerStart(String ipAddress) throws Exception {

    // the connection port is set to 4000 by default
    this.server = new ServerSocket(4000, 0, InetAddress.getByName(ipAddress));
    
    maxClientConnectionAllowed = 5;
    threadPoolSize = 10;
    threadPool = ThreadPool.getInstance(threadPoolSize);
    Cache.getInstance();
    NumberProcessor.getInstance();
  }

  public static void main(String[] args) throws Exception {

    if (args.length == 0) {
      System.out.println(
          "Error: enter a valid host server address to continue..\nUsage: java -jar jarfilepath"
              + " serveraddress\nFor ex: use 'localhost' as serveraddress");
      System.exit(0);
    }

    app = new ServerStart(args[0]);
    System.out.println("Starting up server....\nRunning Server: " + "Host="
        + app.getSocketAddress().getHostAddress() + " Port=" + app.getPort());

    app.systemThreads();

    // main monitoring loop
    while (true) {
      if (ClientHandler.initiateShutdown.get()) {
        app.terminateApp();
      }
      Thread.sleep(1);
    }
  }

  
  private void systemThreads() {
    threadPool.createServerListener(server);
    threadPool.createFileSaver();
    threadPool.createReporter();
  }

  public void terminateApp() {
    threadPool.terminateThreads();
    System.out.println("Server shutting down!");
    System.exit(0);
  }

  public InetAddress getSocketAddress() {
    return this.server.getInetAddress();
  }

  public int getPort() {
    return this.server.getLocalPort();
  }
}
