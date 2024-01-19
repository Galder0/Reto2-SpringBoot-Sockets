package com.reto.elorchatS.Messages.Service;

import java.util.List;

import com.reto.elorchatS.Messages.Model.Message;
import com.reto.elorchatS.Messages.Model.MessageDAO;

public interface MessageService {

	Message createMessage(String messageContent, Integer userId, Integer chatId);

	List<MessageDAO> getAllMessages();

	List<MessageDAO> findMessagesByChatId(Integer chatId);
	
}
