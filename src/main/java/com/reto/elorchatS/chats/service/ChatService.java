package com.reto.elorchatS.chats.service;

import java.util.List;
import java.util.Optional;

import com.reto.elorchatS.chats.model.Chat;

public interface ChatService {

	List<Chat> findAllChats();

	Optional<Chat> findChatById(Integer chatId);

	Chat createChat(Chat newChat);

	Chat updateChat(Integer chatId, Chat updatedChat);

	void deleteChat(Integer chatId);
	
	List<Chat> findChatsByUserId(Integer userId);
	
}
