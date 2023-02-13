package com.example.mdbspringboot.websocket;

import java.security.Principal;

/**
 * A simple simple implementation of {@link java.security.Principal}.
 */
public class TestPrincipal  implements Principal {

	private final String name;


	public TestPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
