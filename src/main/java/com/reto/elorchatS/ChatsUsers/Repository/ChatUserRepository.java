package com.reto.elorchatS.ChatsUsers.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.elorchatS.ChatsUsers.model.ChatUser;

public interface ChatUserRepository extends JpaRepository<ChatUser, Integer>{

	ChatUser findByUserIdAndChatId(Integer userId, Integer chatId);

	
	

}
