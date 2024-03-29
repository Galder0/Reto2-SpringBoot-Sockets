package com.reto.elorchatS.ChatsUsers.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto.elorchatS.ChatsUsers.Repository.ChatUserRepository;
import com.reto.elorchatS.ChatsUsers.model.ChatUser;
import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.repository.ChatRepository;
import com.reto.elorchatS.chats.service.ChatService;
import com.reto.elorchatS.users.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ChatUserServiceImpl implements ChatUserService{

 @Autowired
 ChatRepository chatRepository;

 @Autowired
 UserRepository userRepository;
    
 @Autowired
 ChatUserRepository chatUserRepository;
 
	@Override
	public void joinChat(Integer chatId, Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaveChat(Integer chatId, Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ChatUser> findAllChatUsers() {
		// TODO Auto-generated method stub
		return chatUserRepository.findAll();
	}

	@Override
	public ChatUser createChatUser(ChatUser chatUser) {
	    // Assuming you don't want to create a chat user with a specific ID
	    chatUser.setId(null);

	    // Additional validation or business logic if needed

	    // Check if a user with the same user_id and chat_id already exists
	    ChatUser existingChatUser = chatUserRepository.findByUserIdAndChatId(chatUser.getUserId(), chatUser.getChatId());

	    if (existingChatUser != null) {
	        // User is already in the chat, you can handle this case as needed
	        // For example, throw an exception or return existingChatUser
	        throw new IllegalArgumentException("User is already in the chat");
	    }

	    return chatUserRepository.save(chatUser);
	}

	@Override
	public void deleteChatUser(Integer userId, Integer chatId) {
	    // Assuming your repository has a method to find a chat user by user ID and chat ID
	    ChatUser chatUser = (ChatUser) chatUserRepository.findByUserIdAndChatId(userId, chatId);
	    // Assuming you have a method in the repository to delete the chat user
	    chatUserRepository.delete(chatUser);
	}
//    @Override
//    @Transactional
//    public void joinChat(Integer chatId, Integer userId) {
//        Optional<Chat> optionalChat = chatRepository.findById(chatId);
//        Optional<User> optionalUser = userRepository.findByIdO(userId);
//
//        if (optionalChat.isPresent() && optionalUser.isPresent()) {
//            Chat chat = optionalChat.get();
//            User user = optionalUser.get();
//
//            // Check if the user is not already in the chat
//            if (!chat.getUsers().contains(user)) {
//                chat.addUser(user);
//                chatRepository.save(chat);
//            }
//        } else {
//            throw new IllegalArgumentException("Chat or User not found");
//        }
//    }
//
//    @Override
//    @Transactional
//    public void leaveChat(Integer chatId, Integer userId) {
//        Optional<Chat> optionalChat = chatRepository.findById(chatId);
//        Optional<User> optionalUser = userRepository.findById(userId);
//
//        if (optionalChat.isPresent() && optionalUser.isPresent()) {
//            Chat chat = optionalChat.get();
//            User user = optionalUser.get();
//
//            // Check if the user is not the admin and is in the chat from the start
//            if (!user.isAdmin() && chat.getUsers().contains(user)) {
//                chat.removeUser(user);
//                chatRepository.save(chat);
//            }
//        } else {
//            throw new IllegalArgumentException("Chat or User not found");
//        }
//    }

	@Override
	public List<ChatUser> findChatUsersFromUserId(Integer userId) {
	    // TODO: Implement the logic to query your database or data source
	    // Example: Assuming you have a method in your data repository to fetch chat users by user ID
	    // Replace "userRepository" with your actual repository class
	    // Replace "getChatUsersByUserId" with your actual method name
	    return chatUserRepository.getChatUsersByUserId(userId);
	}

//	@Override
//	public List<ChatUser> findAdminChatsByUserId(Integer userId) {
//	    // Assuming you have a repository method to retrieve admin chats by user id
//	    List<ChatUser> adminChatUsers = chatUserRepository.findByUserIdAndAdmin(userId, true);
//
//	    // You might want to handle the case when no admin chats are found
//	    if (adminChatUsers.isEmpty()) {
//	        return Collections.emptyList();
//	    }
//
//	    // If you need to retrieve full chat information, you can do that here
//	    List<Integer> adminChatIds = adminChatUsers.stream()
//	            .map(ChatUser::getChatId)
//	            .collect(Collectors.toList());
//
//	    // Retrieve full chat information using the chatUserRepository
//	    return chatUserRepository.findByChatIdIn(adminChatIds);
//	}
}
