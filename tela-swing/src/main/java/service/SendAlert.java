/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.IOException;


public class SendAlert {

  private static String webhookUrl = "https://hooks.slack.com/services/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";
  private static String slackChannel = "alertas";

  public static void sendMessage(String message) throws IOException {
    try {
      Payload payload = Payload.builder()
              .channel(slackChannel)
              .username("MyBot")
              .text(message)
              .build();

      WebhookResponse response = Slack.getInstance().send(webhookUrl, payload);
    } catch (Exception e) {
      System.out.printf("Error sending message: %s", e.getMessage());
    }
  }
}
