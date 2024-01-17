package com.reto.elorchatS.chats.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	@Override
	public Chat createChat(Chat newChat) {
	    return chatRepository.save(newChat);
	}

	@Override
    public Chat updateChat(Integer chatId, Chat updatedChat) {
        if (chatRepository.existsById(chatId)) {
            updatedChat.setId(chatId);
            return chatRepository.save(updatedChat);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found with id: " + chatId);
        }
    }

    @Override
    public void deleteChat(Integer chatId) {
        chatRepository.deleteById(chatId);
    }

}
