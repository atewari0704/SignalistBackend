package com.signallist.controller;

import com.signallist.service.WatchlistService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

  private WatchlistService service;

  public WatchlistController(WatchlistService service) {
    this.service = service;
  }

  // Let's get all the watchlist for a user with a given email address
  @GetMapping("/{userId}")
  public List<String> getWatchListSymbols(@PathVariable("userId") String userId) {
    return service.getWatchListSymbols(userId);
  }


  @GetMapping("/health")
  public String healthCheck() {
    return "Watchlist Service is healthy";
  }


  @DeleteMapping("/{userId}/{symbol}")
  public boolean removeStockFromWatchlist(
      @PathVariable("userId") String userId,
      @PathVariable("symbol") String symbol){
    return service.removeStockFromWatchlist(userId, symbol);
  }


  @Data
  public static class AddStockRequest {
    private String userId;
    private String symbol;
    private String company;
  }

  @PutMapping("/addStock")
  public boolean addStockToWatchlist(@RequestBody AddStockRequest request){
    return service.addStockToWatchlist(request.getUserId(), request.getSymbol(), request.getCompany());
  }


  @GetMapping("/{userId}/{symbol}")
  public boolean isStockInWatchlist(
      @PathVariable("userId") String userId,
      @PathVariable("symbol") String symbol){
    return service.isStockInWatchlist(userId,symbol);
  }



}
