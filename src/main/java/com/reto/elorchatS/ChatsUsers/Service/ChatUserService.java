package com.reto.elorchatS.ChatsUsers.Service;

import java.util.List;

import com.reto.elorchatS.ChatsUsers.model.ChatUser;

public interface ChatUserService {
	
	void joinChat(Integer chatId, Integer userId);

    void leaveChat(Integer chatId, Integer userId);

	List<ChatUser> findAllChatUsers();

	ChatUser createChatUser(ChatUser chatUser);

	void deleteChatUser(Integer userId, Integer chatId);

}
