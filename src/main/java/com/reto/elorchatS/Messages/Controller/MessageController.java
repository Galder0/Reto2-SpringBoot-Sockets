package com.reto.elorchatS.Messages.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.elorchatS.Messages.Model.Message;
import com.reto.elorchatS.Messages.Service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {	
	
	@Autowired	
	MessageService messageService;
	
	@GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();

        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }
	
	@GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Integer chatId) {
        List<Message> messages = messageService.findMessagesByChatId(chatId);

        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }
	
}
