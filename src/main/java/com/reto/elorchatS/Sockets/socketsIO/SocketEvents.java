package com.reto.elorchatS.Sockets.socketsIO;

public enum SocketEvents {
	
    ON_MESSAGE_RECEIVED("chat message"),
    ON_SEND_MESSAGE("chat message"),
	ON_JOINED_ROOM("room"),
	ON_LEFT_ROOM("room name");
	
    public final String value;
	
	private SocketEvents(String value) {
		this.value = value;
	}
}
