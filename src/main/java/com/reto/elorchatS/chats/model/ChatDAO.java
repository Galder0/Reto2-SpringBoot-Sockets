package com.reto.elorchatS.chats.model;

import java.util.List;

import com.reto.elorchatS.Security.model.UserDAO;

public class ChatDAO {

    private Integer id;
    private String name;
    private Boolean isPrivate;
    private List<UserDAO> users;

    // Constructors, getters, setters, and any additional methods as needed

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<UserDAO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDAO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ChatDAOModel [id=" + id + ", name=" + name + ", isPrivate=" + isPrivate + "]";
    }
}
