package com.reto.elorchatS.chats.model;

public class ChatUserMovementResponse {
	
	private String response;
	
	public ChatUserMovementResponse() {
		
	}
	
	public ChatUserMovementResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "ChatUserMovementResponse [response=" + response + "]";
	}
	
	

}
