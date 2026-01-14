package com.signallist.repository;

import com.signallist.model.Watchlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, String> {
    // Find a single watchlist by userId (similar to Mongoose findOne({ userId }))
    Optional<Watchlist> findByUserId(String userId);
}
