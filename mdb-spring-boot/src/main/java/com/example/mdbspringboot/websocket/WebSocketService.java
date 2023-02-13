package com.example.mdbspringboot.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

	private final SimpMessagingTemplate messagingTemplate;

	public WebSocketService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void sendSatelliteData() {
		// query api for current satellite data
		messagingTemplate.convertAndSend("receive/data", new SatelliteData());
	}

	public void sendPrivateSatelliteData(final String id) {
		// method used for private query
		// need to query api
		messagingTemplate.convertAndSendToUser(id, "/receive/private-data", new SatelliteData());
	}

}
