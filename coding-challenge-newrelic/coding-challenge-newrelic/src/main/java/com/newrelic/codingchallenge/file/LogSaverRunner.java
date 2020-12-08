package com.newrelic.codingchallenge.file;

import com.newrelic.codingchallenge.process.NumberProcessor;

/** 
 * This thread is responsible for persisting the file to the file system. 
 * File is persisted to the memory once every 10 second.
 * The frequency of saving the file can be adjusted to tune
 * performance of the application and achieve higher throughput.
 * 
 * it safely terminates the thread
 */
public class LogSaverRunner implements Runnable {

  private int persistenceFreqInMilliSecs = 10000;
  private boolean run = true;
  
  @Override
  public void run() {
    LogHandler logHandler = LogHandler.getInstance();
    NumberProcessor processor = NumberProcessor.getInstance();
    try {
      while (run) {
        Thread.sleep(persistenceFreqInMilliSecs);
        StringBuffer buffer = processor.getBuffer();
        if (buffer.length() != 0) {
          logHandler.appendToFile(buffer.toString());
          processor.emptyBuffer();
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void terminate() {
    run = false;
  }
}
