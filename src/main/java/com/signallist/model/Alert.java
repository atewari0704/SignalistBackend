package com.signallist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "alerts")
public class Alert {
  @Id
  private String id; // Matches the top-level "_id"

  private String userId; // Matches "userId"

  // This maps the "alerts" array from your screenshot
  private List<AlertItem> alerts;

  // Inner class to represent the objects INSIDE the array
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AlertItem {
    @Field("_id")
    private String id; // The java object has id but mongo will auto map that to "_id" sometimes
    // so we are just going to be explicit here.

    private String symbol;       // "AAPL"
    private Double targetPrice;  // 277.25
    private String condition;    // "ABOVE"
    private String status;       // "ACTIVE"
    private Instant createdAt;   // Maps the ISO date string automatically
  }
}
