package com.reto.elorchatS.Messages.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.reto.elorchatS.Messages.Model.Message;
import com.reto.elorchatS.Messages.Repository.MessageRepository;
import com.reto.elorchatS.chats.service.ChatService;
import com.reto.elorchatS.users.Service.UserService;

public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ChatService chatService;
	
	
	
	@Override
	public Message createMessage(String messageContent, Integer userId, Integer chatId) {
	    Message newMessage = new Message();
	    newMessage.setMessage(messageContent);

	    // Set the user
	    userService.findUserById(userId).ifPresent(newMessage::setUser);

	    // Set the chat
	    chatService.findChatById(chatId).ifPresent(newMessage::setChat);

	    // Set the timestamp
	    newMessage.setCreatedAt(LocalDateTime.now());

	    // Save the message
	    return messageRepository.save(newMessage);
	}

}
