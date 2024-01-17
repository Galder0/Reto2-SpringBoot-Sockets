package com.reto.elorchatS.Messages.Service;

import com.reto.elorchatS.Messages.Model.Message;

public interface MessageService {

	Message createMessage(String messageContent, Integer userId, Integer chatId);
	
}
