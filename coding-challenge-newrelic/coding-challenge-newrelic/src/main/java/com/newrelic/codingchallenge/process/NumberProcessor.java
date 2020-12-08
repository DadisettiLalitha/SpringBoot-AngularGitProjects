package com.newrelic.codingchallenge.process;

import com.newrelic.codingchallenge.cache.Cache;
import com.newrelic.codingchallenge.file.LogHandler;
import com.newrelic.codingchallenge.Reports.GenerateReports;

/** 
 * 
 * this class handle the processing of the message
 * maintains a temporary buffer while is persisted
 * to the file systems periodically
 */
public class NumberProcessor implements Processor {

  private static NumberProcessor instance = null;

  private StringBuffer buffer;

  private NumberProcessor() {
    LogHandler.getInstance();
    buffer = new StringBuffer();
  }
  
  public static NumberProcessor getInstance() {
    if (instance == null) {
      instance = new NumberProcessor();
    }
    return instance;
  }
    
  @Override
  public void process(String data) {
   
    boolean newData = Cache.getInstance().add(data);
    if(newData) {
//      System.out.println("New Data: " + data);
    	GenerateReports.addUniqueCount();
    	GenerateReports.addUniqueTotalCount();
      // append data to buffer
      appendToBuffer(data);
    }
    else {
//      System.out.println("Duplicate Data: " + data);
      GenerateReports.addDuplicateCount();
    }
  }
  
  public void appendToBuffer(String str) {
    buffer.append(str);
  }
  
  public StringBuffer getBuffer() {
    return buffer;
  }
  
  public void emptyBuffer() {
    buffer = new StringBuffer();
  }

}
