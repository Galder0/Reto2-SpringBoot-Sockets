package com.reto.elorchatS.socketsIO;

public enum SocketEvents {
	
    ON_MESSAGE_RECEIVED("chat message"),
    ON_SEND_MESSAGE("chat message");
	
    public final String value;
	
	private SocketEvents(String value) {
		this.value = value;
	}
}
