package com.reto.elorchatS.Messages.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto.elorchatS.Messages.Model.Message;
import com.reto.elorchatS.Messages.Model.MessageDAO;
import com.reto.elorchatS.Messages.Repository.MessageRepository;
import com.reto.elorchatS.chats.service.ChatService;
import com.reto.elorchatS.users.Service.UserService;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public Message createMessage(String messageContent, Integer userId, Integer chatId) {
	    Message newMessage = new Message();
	    newMessage.setMessage(messageContent);

	    // Set the user and username
	    userService.findUserById(userId).ifPresent(user -> {
	        newMessage.setUser(user);
	        newMessage.setName(user.getName()); // Assuming there's a getUsername() method in your User class
	    });

	    // Set the chat
	    chatService.findChatById(chatId).ifPresent(newMessage::setChat);

	    // Set the timestamp
	    newMessage.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	    System.out.println(newMessage.toString());

	    // Save the message
	    return messageRepository.save(newMessage);
	}


	@Override
    public List<MessageDAO> getAllMessages() {
        List<Message> messages = (List<Message>) messageRepository.findAll();
        return messages.stream()
                .map(this::convertToMessageDAO)
                .collect(Collectors.toList());
    }

	@Override
    public List<MessageDAO> findMessagesByChatId(Integer chatId) {
        // Implement this method in your MessageRepository
		List<Message> messages = (List<Message>) messageRepository.findMessagesByChatId(chatId);
        
        return messages.stream()
                .map(this::convertToMessageDAO)
                .collect(Collectors.toList());
    }
	
	
	private MessageDAO convertToMessageDAO(Message message) {
	    modelMapper.typeMap(Message.class, MessageDAO.class)
	            .addMappings(mapper -> {
	                // Choose the appropriate source property for userName
	                mapper.map(src -> src.getUser().getName(), MessageDAO::setName);
	                // If the above line doesn't work, try the following alternatives:
	                // mapper.map(src -> src.getUser().getName(), MessageDAO::setUserName);
	                // mapper.map(Message::getUserName, MessageDAO::setUserName);
	            });

	    return modelMapper.map(message, MessageDAO.class);
	}

	

}
