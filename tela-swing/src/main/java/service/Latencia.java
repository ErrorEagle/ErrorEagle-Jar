/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author fernandes
 */
public class Latencia {

  public static Long getLatencia() {

    String host = "www.google.com";

    try {
      InetAddress inetAddress = InetAddress.getByName(host);
      long startTime = System.currentTimeMillis();

      if (inetAddress.isReachable(5000)) {
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        return timeTaken;
      } else {
        System.out.println("Ping failed.");
        return null;
      }
    } catch (IOException e) {
      System.out.println("Error occurred while pinging " + host + ": " + e.getMessage());

    }
    return null;
  }

}
