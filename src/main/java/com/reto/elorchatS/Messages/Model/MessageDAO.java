package com.reto.elorchatS.Messages.Model;

import java.sql.Timestamp;

public class MessageDAO {

    private Integer id;
    private String message;
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



    @Override
    public String toString() {
        return "MessageDAO [id=" + id + ", message=" + message + ", userId=" + userId + ", chatId=" + chatId
                + ", createdAt=" + createdAt + "]";
    }
}