package com.example.mdbspringboot.websocket;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.JsonPathExpectationsHelper;

@RunWith(SpringJUnit4ClassRunner.class)
public class WebSocketControllerTests {
	private WebSocketService wsService;

	private TestMessageChannel clientOutboundChannel;
	
	SimpMessagingTemplate messagingTemplate;

	private TestAnnotationMethodHandler annotationMethodHandler;


	@Before
	public void setup() {
		clientOutboundChannel = new TestMessageChannel();
		
		messagingTemplate = new SimpMessagingTemplate(clientOutboundChannel);

		this.wsService = new WebSocketService(messagingTemplate);
		SatelliteController satelliteController = new SatelliteController(wsService);

		this.annotationMethodHandler = new TestAnnotationMethodHandler(
				new TestMessageChannel(), clientOutboundChannel, new SimpMessagingTemplate(new TestMessageChannel()));

		this.annotationMethodHandler.registerHandler(satelliteController);
		this.annotationMethodHandler.setDestinationPrefixes(Arrays.asList("/ws"));
		this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
		this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
		this.annotationMethodHandler.afterPropertiesSet();
	}


	@Test
	public void getSatelliteData() throws Exception {

		StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
		headers.setSubscriptionId("0");
		headers.setDestination("/ws/positions");
		headers.setSessionId("0");
		headers.setUser(new TestPrincipal("fabrice"));
		headers.setSessionAttributes(new HashMap<>());
		Message<byte[]> message = MessageBuilder.withPayload(new byte[0]).setHeaders(headers).build();

		this.annotationMethodHandler.handleMessage(message);

		assertEquals(1, this.clientOutboundChannel.getMessages().size());
		Message<?> reply = this.clientOutboundChannel.getMessages().get(0);

		StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);
		assertEquals("0", replyHeaders.getSessionId());
		assertEquals("0", replyHeaders.getSubscriptionId());
		assertEquals("/ws/positions", replyHeaders.getDestination());

		String json = new String((byte[]) reply.getPayload(), Charset.forName("UTF-8"));
		Map<String, List<BigDecimal>> expectedMap = new HashMap<>();
		List<BigDecimal> testList = new ArrayList<>();
		testList.add(new BigDecimal(2.2233323));
		expectedMap.put("test", testList);
		BigDecimal expectedEpoch = new BigDecimal(5.6734343);
		new JsonPathExpectationsHelper("$.elements").assertValue(json, expectedMap);
		new JsonPathExpectationsHelper("$.epoch").assertValue(json, expectedEpoch);
	}


	/**
	 * An extension of SimpAnnotationMethodMessageHandler that exposes a (public)
	 * method for manually registering a controller, rather than having it
	 * auto-discovered in the Spring ApplicationContext.
	 */
	private static class TestAnnotationMethodHandler extends SimpAnnotationMethodMessageHandler {

		public TestAnnotationMethodHandler(SubscribableChannel inChannel, MessageChannel outChannel,
				SimpMessageSendingOperations brokerTemplate) {

			super(inChannel, outChannel, brokerTemplate);
		}

		public void registerHandler(Object handler) {
			super.detectHandlerMethods(handler);
		}
	}

}
