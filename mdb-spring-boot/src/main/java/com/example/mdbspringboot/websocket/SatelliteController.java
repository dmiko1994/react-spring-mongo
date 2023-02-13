package com.example.mdbspringboot.websocket;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SatelliteController {
	private static final Log logger = LogFactory.getLog(SatelliteController.class);

	private final WebSocketService wsService;
	
	@Autowired
	public SatelliteController(WebSocketService wsService) {
		this.wsService = wsService;
	}
	
	@SubscribeMapping("/positions")
	public SatelliteData getPositions(Principal principal) {
		logger.debug("Positions for " + principal.getName());
		//query?
		Map<String, List<BigDecimal>> expectedMap = new HashMap<>();
		List<BigDecimal> testList = new ArrayList<>();
		testList.add(new BigDecimal(2.2233323));
		expectedMap.put("test", testList);
		BigDecimal expectedEpoch = new BigDecimal(5.6734343);
		SatelliteData satelliteData = new SatelliteData();
		satelliteData.setElements(expectedMap);
		satelliteData.setEpoch(expectedEpoch);
		return satelliteData;
	}
	
	@MessageMapping("/data")
	@SendTo("/receive/data")
	public SatelliteData getData() throws InterruptedException {
		System.out.println("received message");
		// need to query the api to retrieve current satellite data, but leaving this
		// comment here for now
		return new SatelliteData();
	}

	@MessageMapping("/private-data")
	@SendToUser("/receive/private-data")
	public SatelliteData getPrivateSatelliteData(final Principal principal) throws InterruptedException {
		// if you had multiple instances of the UI, and only wanted to send satellite
		// data to the one requesting it, not broadcast to all instances, you would use
		// this mapping
		System.out.println("received message private" + principal.getName());
		// query the data, dont return newly instantiated object
		return new SatelliteData();
	}
}
