package com.newrelic.codingchallenge;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.newrelic.codingchallenge.clientinputhandler.ClientHandler;
import com.newrelic.codingchallenge.clientinputhandler.ClientConnectionHandler;
import com.newrelic.codingchallenge.clientinputhandler.RefuseConnectionHandler;
import com.newrelic.codingchallenge.clientinputhandler.ServerListener;
import com.newrelic.codingchallenge.file.LogSaverRunner;
import com.newrelic.codingchallenge.Reports.GenerateReports;

/**
 * this class handles system thread pool.
 *
 * handles execution life cycle of server monitoring thread, server 
 * port listener thread all the active client threads, connection 
 * refused to a client thread and file saver thread. It is also  
 * responsible for destruction of the threads during application 
 * termination.
 */
public class ThreadPool {

  private static ThreadPool instance = null;
  private static ExecutorService executor;

  private Runnable ReporterWorker;
  private Runnable serverListenerWorker;
  private Runnable fileSaverWorker;

  public static ThreadPool getInstance(int size) {
    if (instance == null) {
      instance = new ThreadPool(size);
    }
    return instance;
  }

  private ThreadPool() {}

  private ThreadPool(int size) {
    executor = Executors.newFixedThreadPool(size);
  }

  public void createClientHandler(Socket clientSocket) {
    Runnable worker = new ClientHandler(clientSocket);
    ClientConnectionHandler.addSocket(clientSocket);
    executor.execute(worker);
  }

  public void createServerListener(ServerSocket server) {
    serverListenerWorker = new ServerListener(server, this);
    executor.execute(serverListenerWorker);
  }

  public void createReporter() {
    ReporterWorker = new GenerateReports();
    executor.execute(ReporterWorker);
  }

  public void createFileSaver() {
    fileSaverWorker = new LogSaverRunner();
    executor.execute(fileSaverWorker);
  }
  
  public void createRefuseConnectionHandler(Socket clientSocket) {
    Runnable refuseConnectionWorker = new RefuseConnectionHandler(clientSocket);
    executor.execute(refuseConnectionWorker);
  }

  public void terminateThreads() {

	  ClientConnectionHandler.terminateAllClientConnections();
    // terminate metrics thread
    ((GenerateReports) ReporterWorker).terminate();
    // terminate listener
    ((ServerListener) serverListenerWorker).terminate();
    // terminate file saver
    ((LogSaverRunner) fileSaverWorker).terminate();
    try {
      // wait till the File saver is safely terminated
      // to prevent data loss
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    }
    executor.shutdownNow();
    System.out.println("Executer terminated : " + executor.isTerminated());
  }
}
