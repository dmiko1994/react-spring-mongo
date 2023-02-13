package com.example.mdbspringboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketRestController {

  @Autowired
  WebSocketService wsService;

  @GetMapping("/retrieve-satellite-data")
  public void sendSatelliteData(){
    wsService.sendSatelliteData();
  }
  
  @GetMapping("/retrieve-private-satellite-data/{id}")
  public void sendPrivateSatelliteData(@PathVariable String id){
    wsService.sendPrivateSatelliteData(id);
  }
}
