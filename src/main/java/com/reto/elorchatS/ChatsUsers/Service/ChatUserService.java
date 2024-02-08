package com.reto.elorchatS.ChatsUsers.Service;

import java.util.List;

import com.reto.elorchatS.ChatsUsers.model.ChatUser;
import com.reto.elorchatS.chats.model.Chat;

public interface ChatUserService {
	
	void joinChat(Integer chatId, Integer userId);

    void leaveChat(Integer chatId, Integer userId);
    
    List<ChatUser> findChatUsersFromUserId(Integer UserId);

	List<ChatUser> findAllChatUsers();

	ChatUser createChatUser(ChatUser chatUser);

	void deleteChatUser(Integer userId, Integer chatId);

//	List<ChatUser> findAdminChatsByUserId(Integer userId);

}
