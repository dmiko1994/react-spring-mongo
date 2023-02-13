package com.example.mdbspringboot.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/satellite")
public class WebSocketServer {
	@OnOpen
	public void open(Session session) {
		
	}
	
	@OnClose
	public void onClose(Session session) {
		
	}
	
	@OnError
	public void onError(Throwable error) {
		
	}
	
	@OnMessage
	public void handleMessage(String message, Session session) {
		
	}
}
