package com.reto.elorchatS.ChatsUsers.model;

public class ChatUserEmailRequest {
	
	private String email;
	
	private Integer chatId;
	
	public ChatUserEmailRequest(){
		
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	@Override
	public String toString() {
		return "ChatUserEmailRequest [email=" + email + ", chatId=" + chatId + "]";
	}
	
	

}
