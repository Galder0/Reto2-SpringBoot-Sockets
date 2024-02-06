package com.reto.elorchatS.Messages.Model;

import java.sql.Timestamp;

public class MessageDAO {

    private Integer id;
    private String message;
    private String image;
    private String name;
    private Integer userId;
    private Integer chatId;
    private Timestamp createdAt;
    public MessageDAO() {

    }

    public MessageDAO(Integer id, String message, Integer userId, Integer chatId, Timestamp createdAt) {
        super();
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.chatId = chatId;
        this.createdAt = createdAt;
    }
    

	public MessageDAO(Integer id, String message, String name, Integer userId, Integer chatId, Timestamp createdAt) {
		super();
		this.id = id;
		this.message = message;
		this.name = name;
		this.userId = userId;
		this.chatId = chatId;
		this.createdAt = createdAt;
	}
	
	public MessageDAO(String message, String name, Integer userId, Integer chatId, Timestamp createdAt) {
		super();
		this.message = message;
		this.name = name;
		this.userId = userId;
		this.chatId = chatId;
		this.createdAt = createdAt;
	}
	
	
	public MessageDAO( String message, String name, Integer userId, Integer chatId) {
        super();
        this.message = message;
        this.name = name;
        this.userId = userId;
        this.chatId = chatId;

    }
	
	public MessageDAO( String message,String image, String name, Integer userId, Integer chatId) {
        super();
        this.message = message;
        this.image = image;
        this.name = name;
        this.userId = userId;
        this.chatId = chatId;

    }

	public MessageDAO(Integer id, String message, String image, String name, Integer userId, Integer chatId,
			Timestamp createdAt) {
		super();
		this.id = id;
		this.message = message;
		this.image = image;
		this.name = name;
		this.userId = userId;
		this.chatId = chatId;
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MessageDAO [id=" + id + ", message=" + message + ", image=" + image + ", name=" + name + ", userId="
				+ userId + ", chatId=" + chatId + ", createdAt=" + createdAt + "]";
	}


}