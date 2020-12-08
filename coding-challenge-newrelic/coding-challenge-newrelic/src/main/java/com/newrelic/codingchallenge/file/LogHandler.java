package com.newrelic.codingchallenge.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this class handles the creation,  
 * deletion (when application starts)
 * and continuously appending the file when 
 * application is running.
 */
public class LogHandler {

  private static LogHandler instance = null;

  private final String filename = "C:/workspace-STS-4-4.6/coding-challenge-newrelic/coding-challenge-newrelic/logs/numbers.log";

  private LogHandler() {
    deleteFile();
  }

  public static LogHandler getInstance() {
    if (instance == null) {
      instance = new LogHandler();
    }
    return instance;
  }

  private void deleteFile() {
    File file = new File(filename);
    file.delete();
//    if (file.delete()) {
//      System.out.println("File deleted successfully");
//    } else {
//      System.out.println("Failed to delete the file");
//    }
  }

  public void appendToFile(String str) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
      writer.append(str);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
