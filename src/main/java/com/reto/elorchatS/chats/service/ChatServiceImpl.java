package com.reto.elorchatS.chats.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatRepository chatRepository;

	@Override
	public List<Chat> findAllChats() {
		// TODO Auto-generated method stub
		return (List<Chat>) chatRepository.findAll();
	}

	@Override
	public Optional<Chat> findChatById(Integer chatId) {
		// TODO Auto-generated method stub
		return chatRepository.findById(chatId);
	}

}
