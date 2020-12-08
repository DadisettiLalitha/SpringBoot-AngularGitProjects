package com.newrelic.codingchallenge.clientcall;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;

/**
 * this thread is an automated client response generator
 * specially made for the purpose of testing application 
 * performance.
 */
public class AutoDataGenerate implements Runnable {

  private Socket socket;
  private ClientStart clientStart;
  private int maxRecordsNum;
  private int maxDigitsInNumGenerated; 

  public AutoDataGenerate(ClientStart clientStart) {
    maxRecordsNum = 500000;
    maxDigitsInNumGenerated = 1000000000;
    this.clientStart = clientStart;
    this.socket = clientStart.getSocket();
  }

  @Override
  public void run() {
    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
      String input;
      System.out.println("Generating " + maxRecordsNum + " messages.");
      Random r = new Random();
      for (int i = 0; i < maxRecordsNum; i++) {
        int num = r.nextInt(maxDigitsInNumGenerated);
        input = String.valueOf(num).concat(ClientStart.serverNewlineChar);
        input = StringUtils.leftPad(input, ClientStart.maxStringLength, "0");
        out.println(input);
      }
      clientStart.terminateClient();
    } catch (IOException e) {
    }
  }
}
