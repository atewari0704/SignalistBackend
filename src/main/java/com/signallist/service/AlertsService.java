package com.signallist.service;

import com.signallist.model.Alert;
import com.signallist.repository.AlertsRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AlertsService {
  public final AlertsRepository repository;
  private final MongoTemplate mongoTemplate;

  public AlertsService(AlertsRepository alertRepository, MongoTemplate mongoTemplate) {
    this.repository = alertRepository;
    this.mongoTemplate = mongoTemplate;
  }

  public List<Alert.AlertItem> getAlerts(String userId){
    List<Alert> alerts = repository.findByUserId(userId);
    return alerts.stream()
        .flatMap(alert -> alert.getAlerts().stream())
        .toList();
  }

  /**
   * Add a new alert for a specific stock.
   * @param userId - The user ID to add the alert for.
   * @param symbol - The stock symbol to add an alert for.
   * @param targetPrice - The target price for the alert.
   * @param condition - The condition for the alert (e.g., "ABOVE" or "BELOW").
   * @return The newly added alert if successful, null otherwise.
   */
  public Alert.AlertItem addAlert(String userId, String symbol, Double targetPrice,
      String condition) {
    // Create new alert item
    Alert.AlertItem newAlert = new Alert.AlertItem();
    newAlert.setId(new ObjectId().toHexString());
    newAlert.setSymbol(symbol);
    newAlert.setTargetPrice(targetPrice);
    newAlert.setCondition(condition);
    newAlert.setStatus("ACTIVE");
    newAlert.setCreatedAt(Instant.now());

    // Use upsert to insert or update the document
    Query query = new Query(Criteria.where("userId").is(userId));
    Update update = new Update()
        .setOnInsert("userId", userId)
        .push("alerts", newAlert);

    var result = mongoTemplate.upsert(query, update, Alert.class);

    // Return the new alert only if the operation was successful
    if (result.getModifiedCount() > 0 || result.getUpsertedId() != null) {
      return newAlert;
    }

    return null;
  }


  /**
   * Deletes an alert for a specific user based on the userId and alertId.
   * @param userId - The user ID to delete the alert for.
   * @param id - The ID of the alert to be deleted.
   * tries to remove the alert based on both if the id is stored as a string or as an ObjectId.
   */
  public boolean deleteAlert(String userId, String id) {
      Query query = new Query(Criteria.where("userId").is(userId));
      Update update = new Update().pull("alerts",
          Query.query(Criteria.where("_id").in(id, new ObjectId(id))));

      var result = mongoTemplate.updateFirst(query, update, Alert.class);
      return result.getModifiedCount() > 0;
  }


}
