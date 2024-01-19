package com.reto.elorchatS.chats.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reto.elorchatS.users.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean Private;
    
    @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<User> users;

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
		return Private;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.Private = isPrivate;
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", name=" + name + ", isPrivate=" + Private + "]";
	}

	public Object getUsers() {
		// TODO Auto-generated method stub
		return users;
	}
    

    // Getters and setters, and any additional methods as needed

}