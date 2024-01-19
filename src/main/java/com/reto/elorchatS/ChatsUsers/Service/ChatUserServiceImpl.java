package com.reto.elorchatS.ChatsUsers.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.repository.ChatRepository;
import com.reto.elorchatS.users.model.User;
import com.reto.elorchatS.users.repository.UserRepository;

import jakarta.transaction.Transactional;

public class ChatUserServiceImpl implements ChatUserService{

 @Autowired
 ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

	@Override
	public void joinChat(Integer chatId, Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaveChat(Integer chatId, Integer userId) {
		// TODO Auto-generated method stub
		
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

}
