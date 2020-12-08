package com.newrelic.codingchallenge.clientcall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * this thread handles response sent from the server.
 * messages : null/connected/termination
 */
public class ServerResponseHandler implements Runnable {

  private Socket socket;
  private ClientStart clientStart;

  public ServerResponseHandler(ClientStart clientStart) {
    this.clientStart = clientStart;
    this.socket = clientStart.getSocket();
  }

  @Override
  public void run() {
    String response;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      while (true) {
        response = br.readLine();
        if (response == null) {
          System.out.println("Server shutting down!");
          clientStart.terminateClient();
          break;
        } else if (response.equals("Connected")) {
          System.out.println("Connected to Server..");
        } else if (response.equals("Connection Refused")) {
          System.out.println("Connection refused by Server!");
          clientStart.terminateClient();
        }
      }
    } catch (IOException e) {
    }
  }
}
