package com.reto.elorchatS.ChatsUsers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_user")
public class ChatUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;
	
	
	@Column(name = "chat_id")
	private Integer chatId;
	
	@Column(name = "isadmin")
	private Boolean isAdmin;
	
	public ChatUser() {
		
	}

	public ChatUser(Integer id, Integer userId, Integer chatId, Boolean isAdmin) {
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

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "prueba [id=" + id + ", userId=" + userId + ", chatId=" + chatId + ", isAdmin=" + isAdmin + "]";
	}

}
