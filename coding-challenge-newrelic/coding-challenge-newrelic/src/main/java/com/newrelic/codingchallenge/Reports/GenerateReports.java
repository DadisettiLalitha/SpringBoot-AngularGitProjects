package com.newrelic.codingchallenge.Reports;

/**
 * This thread is responsible for display report
 * once in every tens seconds. 
 */
public class GenerateReports implements Runnable {

  private static int uniqueCount;
  private static int duplicateCount;
  private static int uniqueTotalCount;

  private boolean run = true;

  @Override
  public void run() {
    while (run) {
      try {
        String fs = String.format("Received %d new unique numbers, %d new duplicates. Application total unique: %d",
            uniqueCount, duplicateCount, uniqueTotalCount);
        System.out.println("\nServer data processing report: \n" + fs);
        resetCounters();
        Thread.sleep(10000);
      } catch (InterruptedException e) {
      }
    }
  }

  private void resetCounters() {
    uniqueCount = 0;
    duplicateCount = 0;
  }

  public void terminate() {
    run = false;
  }

  public static void addUniqueCount() {
    uniqueCount++;
  }

  public static void addDuplicateCount() {
    duplicateCount++;
  }

  public static void addUniqueTotalCount() {
    uniqueTotalCount++;
  }

}
