package com.reto.elorchatS.Messages.Service;

import java.util.List;

import com.reto.elorchatS.Messages.Model.Message;

public interface MessageService {

	Message createMessage(String messageContent, Integer userId, Integer chatId);

	List<Message> getAllMessages();

	List<Message> findMessagesByChatId(Integer chatId);
	
}
