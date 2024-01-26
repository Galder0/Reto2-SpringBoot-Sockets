package com.reto.elorchatS.chats.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reto.elorchatS.ChatsUsers.Service.ChatUserService;
import com.reto.elorchatS.ChatsUsers.model.ChatUser;
import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.service.ChatService;

@RestController
@RequestMapping("api/chats")
public class ChatController {

	@Autowired
	ChatService chatService;
	
	@Autowired
	ChatUserService chatUserService;
	
	 @GetMapping
	    public List<Chat> getAllChats() {
	        return chatService.findAllChats();
	    }
	
	 @GetMapping("/{chatId}")
	    public ResponseEntity<Chat> findChatById(@PathVariable Integer chatId) {
	    	Chat chat = chatService.findChatById(chatId).orElseThrow(
	    		() -> new ResponseStatusException(HttpStatus.NO_CONTENT)
	    	);
	    	System.out.println(chat.toString());
	    	return new ResponseEntity<Chat>(chat, HttpStatus.ACCEPTED);
	}
	
	 @PostMapping
	 public ResponseEntity<Integer> createChat(@RequestBody Chat newChat) {
	     int chatId = chatService.createChat(newChat);
	     
	     // Assuming that chatService.createChat returns the ID of the created chat

	     // Return the chat ID in the response body with HTTP status 201 CREATED
	     return new ResponseEntity<>(chatId, HttpStatus.CREATED);
	 }

    @PutMapping("/{chatId}")
    public ResponseEntity<Chat> updateChat(@PathVariable Integer chatId, @RequestBody Chat updatedChat) {
        Chat chat = chatService.updateChat(chatId, updatedChat);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Integer chatId) {
        chatService.deleteChat(chatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/Users")
    public List<ChatUser> getAllChatUsers() {
        return chatUserService.findAllChatUsers();
    }
    
    @PostMapping("/joinChat")
    public ResponseEntity<ChatUser> createChatUser(@RequestBody ChatUser chatUser) {
        // Additional validation or business logic if needed

        ChatUser createdChatUser = chatUserService.createChatUser(chatUser);

        return new ResponseEntity<>(createdChatUser, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/leaveChat/{userId}/{chatId}")
    public ResponseEntity<Void> deleteChatUser(@PathVariable Integer userId, @PathVariable Integer chatId) {
        chatUserService.deleteChatUser(userId, chatId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
