package com.signallist.service;

import com.mongodb.client.result.UpdateResult;
import com.signallist.model.Watchlist;
import com.signallist.repository.UserRepository;
import com.signallist.repository.WatchlistRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class WatchlistService {
  private final WatchlistRepository repository;
  private final UserRepository userRepository; // to be able to get the userId from email
  private final MongoTemplate mongoTemplate;

  public WatchlistService(WatchlistRepository repository, UserRepository userRepository,
      MongoTemplate mongoTemplate) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.mongoTemplate = mongoTemplate;
  }

  // Method was for testing will remove later
  public String getUserIdFromEmail(String email){
    return userRepository.findIdByEmail(email)
      .map(idWrapper -> idWrapper.getId())
      .orElse(null);
  }


  public List<String> getWatchListSymbols(String userId) {
    var watchlistOpt = repository.findByUserId(userId);

    return watchlistOpt
      .map(watchlist -> watchlist.getItems().stream()
        .map(item -> item.getSymbol())
        .distinct()
        .toList())
      .orElse(Collections.emptyList());
  }


  public boolean removeStockFromWatchlist(String userId, String symbol) {
    Query query = new Query(Criteria.where("userId").is(userId));
    Update update = new Update().pull("items", Query.query(Criteria.where("symbol").regex("^" + symbol + "$", "i")));

    UpdateResult result = mongoTemplate.updateFirst(query, update, Watchlist.class);

    return result.getModifiedCount() > 0;
  }

  public boolean addStockToWatchlist(String userId, String symbol, String company) {
    Query query = new Query(Criteria.where("userId").is(userId));

    Watchlist.WatchListItem newItem = new Watchlist.WatchListItem(symbol, company, Instant.now());
    Update update = new Update().push("items", newItem);

    UpdateResult result = mongoTemplate.updateFirst(query, update, Watchlist.class);

    return result.getModifiedCount() > 0;
  }

  public boolean isStockInWatchlist(String userId,String Symbol){
    var watchlistOpt = repository.findByUserId(userId);

    return watchlistOpt
      .map(watchlist -> watchlist.getItems().stream()
        .anyMatch(item -> item.getSymbol().equalsIgnoreCase(Symbol)))
      .orElse(false);
  }

}
