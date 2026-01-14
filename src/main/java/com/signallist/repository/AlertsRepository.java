package com.signallist.repository;

import com.signallist.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertsRepository extends MongoRepository<Alert, String> {
    // Find all alerts for a specific user
    List<Alert> findByUserId(String userId);

    // Find alert by user ID and alert item ID (nested in the alerts array)
    // useful for removing operations
    Optional<Alert> findByUserIdAndAlertsId(String userId, String alertId);

    // Check if an alert exists for a user
    boolean existsByUserId(String userId);
}
