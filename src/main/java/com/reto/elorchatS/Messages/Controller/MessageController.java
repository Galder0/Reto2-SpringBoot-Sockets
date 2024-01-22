package com.reto.elorchatS.Messages.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.elorchatS.Messages.Model.Message;
import com.reto.elorchatS.Messages.Model.MessageDAO;
import com.reto.elorchatS.Messages.Service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired	
	MessageService messageService;
	
	
	
	
	@GetMapping
    public ResponseEntity<List<MessageDAO>> getAllMessages() {
        List<MessageDAO> messages = messageService.getAllMessages();

        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }
	
	@GetMapping("/{chatId}")
	public ResponseEntity<List<MessageDAO>> getMessagesByChatId(@PathVariable Integer chatId) {
	    List<MessageDAO> messageDAOs = messageService.findMessagesByChatId(chatId);

	    if (messageDAOs.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(messageDAOs, HttpStatus.OK);
	    }
	}
	
	@PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody MessageDAO newMessage) {
        try {
            Message createdMessage = messageService.createMessage(newMessage.getMessage(), newMessage.getUserId(), newMessage.getChatId());
            return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions, e.g., validation errors
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
}
