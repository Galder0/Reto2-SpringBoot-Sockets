package com.reto.elorchatS.Sockets.model;

public class JoinRoom {
	
	private String room;

	public JoinRoom() {
		
	}

	public JoinRoom(String room) {
		super();
		this.room = room;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "JoinRoom [room=" + room + "]";
	}


}
