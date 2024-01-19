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

import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.service.ChatService;

@RestController
@RequestMapping("api/chats")
public class ChatController {

	@Autowired
	ChatService chatService;
	
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
	    public ResponseEntity<Chat> createChat(@RequestBody Chat newChat) {
	        Chat createdChat = chatService.createChat(newChat);
	        return new ResponseEntity<>(createdChat, HttpStatus.CREATED);
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
    
}
