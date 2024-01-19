package com.reto.elorchatS.ChatsUsers.model;

public class ChatUser {
	
private Integer id;
	
	private Integer userId;
	
	private Integer chatId;
	
	private boolean isAdmin;
	
	public ChatUser() {
		
	}

	public ChatUser(Integer id, Integer userId, Integer chatId, boolean isAdmin) {
		super();
		this.id = id;
		this.userId = userId;
		this.chatId = chatId;
		this.isAdmin = isAdmin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "prueba [id=" + id + ", userId=" + userId + ", chatId=" + chatId + ", isAdmin=" + isAdmin + "]";
	}

}
