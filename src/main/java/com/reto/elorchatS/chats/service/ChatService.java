package com.reto.elorchatS.chats.service;

import java.util.List;
import java.util.Optional;

import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.users.model.User;

public interface ChatService {

	List<Chat> findAllChats();

	Optional<Chat> findChatById(Integer chatId);
	
	Optional<Chat> findChatByName(String chatName);

	int createChat(Chat newChat);

	Chat updateChat(Integer chatId, Chat updatedChat);

	void deleteChat(Integer chatId);
	
	List<Chat> findChatsByUserId(Integer userId);

	List<Chat> findChatsByIds(List<Integer> adminChatIds);

	List<Chat> getChatsForAdminUser(Integer userId);

	Boolean isUserOnChat(User user, Chat chat);
	
	List<Chat> findAllPublicChats();
	
}
