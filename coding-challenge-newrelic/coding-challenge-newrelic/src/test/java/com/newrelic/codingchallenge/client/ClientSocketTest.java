package com.newrelic.codingchallenge.client;

import com.newrelic.codingchallenge.clientcall.ClientStart;

public class ClientSocketTest {
  
//  @Test
  public void test() throws Exception {
    
    String[] args = {"localhost", "4000"};
    ClientStart.main(args);
    
  }
  
}
