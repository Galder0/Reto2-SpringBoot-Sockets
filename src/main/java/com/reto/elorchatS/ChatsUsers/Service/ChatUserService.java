package com.reto.elorchatS.ChatsUsers.Service;

public interface ChatUserService {
	
	void joinChat(Integer chatId, Integer userId);

    void leaveChat(Integer chatId, Integer userId);

}
