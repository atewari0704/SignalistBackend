package com.signallist.controller;

import com.signallist.model.Alert;
import com.signallist.service.AlertsService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertsController {
  public final AlertsService service;


  public AlertsController(AlertsService service) {
    this.service = service;
  }


  @GetMapping("{userId}")
  public List<Alert.AlertItem> getAlerts( @PathVariable("userId") String userId){
    return service.getAlerts(userId);
  }


  @Data
  public static class AddAlertRequest {
    private String userId;
    private String symbol;       // "AAPL"
    private Double targetPrice;  // 277.25
    private String condition;    // "ABOVE"
  }

  @PostMapping("/addAlert")
  public Alert.AlertItem addAlert(@RequestBody AlertsController.AddAlertRequest request){
    return service.addAlert(request.getUserId(), request.getSymbol(), request.getTargetPrice(), request.getCondition());
  }

  @DeleteMapping("/{userId}/{id}")
  public boolean deleteAlert(
      @PathVariable("userId") String userId,
      @PathVariable("id") String id) {
    return service.deleteAlert(userId, id);
  }

}
