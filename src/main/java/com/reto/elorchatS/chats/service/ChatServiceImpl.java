package com.reto.elorchatS.chats.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.repository.ChatRepository;
import com.reto.elorchatS.users.model.User;

import jakarta.transaction.Transactional;

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
	public int createChat(Chat newChat) {
	    Chat savedChat = chatRepository.save(newChat);

	    // Assuming getId() returns the ID of the saved chat
	    return (savedChat != null && savedChat.getId() > 0) ? savedChat.getId() : 0;
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

    @Override
    public List<Chat> findChatsByUserId(Integer userId) {
        // Implement the logic to fetch chats based on user ID
        return chatRepository.findChatsByUserId(userId);
    }

	@Override
	public Optional<Chat> findChatByName(String chatName) {
		// TODO Auto-generated method stub
		return chatRepository.findByName(chatName);
	}

	public List<Chat> findChatsByIds(List<Integer> chatIds) {
        return (List<Chat>) chatRepository.findAllById(chatIds);
    }

	@Override
    public List<Chat> getChatsForAdminUser(Integer userId) {
        // Assuming you have a method in your repository to handle this query
        // Adjust the method name according to your repository
        return chatRepository.findChatsByAdminAndUserId(userId);
    }

	@Override
	public Boolean isUserOnChat(User user, Chat chat) {
	    if (chat == null || user == null || chat.getUsers() == null) {
	        return false; // Handle null parameters or empty user list
	    }

	    List<User> userList = (List<User>) chat.getUsers();

	    return userList.contains(user);
	}

	@Override
	public Chat getChatFromName(String name) {
	    // Assuming chats are stored in a list or database
	    List<Chat> chats = (List<Chat>) chatRepository.findAll(); // Replace chatRepository with your actual repository or data access object

	    // Iterate through the list of chats to find the one with the matching name
	    for (Chat chat : chats) {
	        if (chat.getName().equals(name)) {
	            // Return the chat if the name matches
	            return chat;
	        }
	    }
	    // If no chat with the specified name is found, return null
	    return null;
	}
  
  @Override
	public List<Chat> findAllPublicChats() {
		return chatRepository.findAllPublicChats();
	}
    
    
    
}
