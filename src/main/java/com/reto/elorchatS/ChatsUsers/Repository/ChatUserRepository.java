package com.reto.elorchatS.ChatsUsers.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.elorchatS.ChatsUsers.model.ChatUser;

public interface ChatUserRepository extends JpaRepository<ChatUser, Integer>{

	ChatUser findByUserIdAndChatId(Integer userId, Integer chatId);

	List<ChatUser> getChatUsersByUserId(Integer userId);

//	List<ChatUser> findByUserIdAndAdmin(Integer userId, boolean b);
//
//	List<ChatUser> findByChatIdIn(List<Integer> adminChatIds);

}
