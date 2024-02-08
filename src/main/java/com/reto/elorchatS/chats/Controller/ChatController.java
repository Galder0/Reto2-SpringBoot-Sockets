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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reto.elorchatS.ChatsUsers.Service.ChatUserService;
import com.reto.elorchatS.ChatsUsers.model.ChatUser;
import com.reto.elorchatS.ChatsUsers.model.ChatUserEmailRequest;
import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.model.ChatUserMovementResponse;
import com.reto.elorchatS.chats.service.ChatService;
import com.reto.elorchatS.users.Service.UserService;
import com.reto.elorchatS.users.model.User;

@RestController
@RequestMapping("api/chats")
public class ChatController {

	@Autowired
	ChatService chatService;
	
	@Autowired
	ChatUserService chatUserService;
	
	@Autowired 
	UserService userService;
	
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
        try {
            // Additional validation or business logic if needed

            ChatUser createdChatUser = chatUserService.createChatUser(chatUser);

            return new ResponseEntity<>(createdChatUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle generic exceptions
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @DeleteMapping("/leaveChat/{userId}/{chatId}")
    public ResponseEntity<Void> deleteChatUser(@PathVariable Integer userId, @PathVariable Integer chatId) {
        try {
            chatUserService.deleteChatUser(userId, chatId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Handle generic exceptions
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @PostMapping("/joinChatEmail")
    public ResponseEntity<ChatUserMovementResponse> joinChat(@RequestBody ChatUserEmailRequest request) {
        try {
            System.out.println("JoinChatEmail: Received request to join chat for email " + request.getEmail());
            
            ChatUserMovementResponse response = new ChatUserMovementResponse();

            // Additional validation or business logic if needed

            // Check if the user with the provided email exists
            User user = userService.findUserByEmail(request.getEmail()).orElse(null);
            if (user == null) {
            	response.setResponse("User Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Check if the chat with the provided ID exists
            Chat chat = chatService.findChatById(request.getChatId()).orElse(null);
            if (chat == null) {
            	response.setResponse("Chat Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }

            // Check if the user is already part of the chat
            Boolean isUserOnChat = chatService.isUserOnChat(user, chat);

            if (isUserOnChat) {
                // User is already part of the chat
            	response.setResponse("User is already in Chat");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                // User is not part of the chat, perform the join operation
                ChatUser chatUser = new ChatUser();
                chatUser.setUserId(user.getId());
                chatUser.setChatId(chat.getId());
                chatUser.setAdmin(false);

                // Call the service method to join the user to the chat
                chatUserService.createChatUser(chatUser);

                System.out.println("JoinChatEmail: User with email " + user.getEmail() + " successfully joined the chat.");
                // You can return a success response if needed
                response.setResponse("User Joined Chat");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
        	ChatUserMovementResponse response = new ChatUserMovementResponse();
        	response.setResponse("Internal Server Error");
            System.err.println("JoinChatEmail: An error occurred while joining the chat. " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/leaveChatEmail")
    public ResponseEntity<ChatUserMovementResponse> leaveChat(@RequestBody ChatUserEmailRequest request) {
        // Additional validation or business logic if needed
    	System.out.println("llego leaveChatEmail");
    	
    	ChatUserMovementResponse response = new ChatUserMovementResponse();
    	try {
            System.out.println("LeaveChatEmail: Received request to leave chat for email " + request.getEmail());
            
        // Check if the user with the provided email exists
        User user = userService.findUserByEmail(request.getEmail()).orElse(null);
        if (user == null) {
        	response.setResponse("User Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if the chat with the provided ID exists
        Chat chat = chatService.findChatById(request.getChatId()).orElse(null);
        if (chat == null) {
        	response.setResponse("Chat Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if the user is part of the chat
        Boolean isUserOnChat = chatService.isUserOnChat(user, chat);

        if (!isUserOnChat) {
            // User is not part of the chat
        	response.setResponse("User Not in Chat");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            // User is part of the chat, perform the leave operation
        	chatUserService.deleteChatUser(user.getId(), chat.getId());

            // You can return a success response if needed
        	response.setResponse("User left Chat");
        	System.out.println("LeaveChatEmail: User with email " + user.getEmail() + " successfully left the chat.");
            return new ResponseEntity<>(HttpStatus.OK);
            
        }
    } catch (Exception e) {
    	response.setResponse("Internal Server Error");
        System.err.println("LeaveChatEmail: An error occurred while leaving the chat. " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
    
//    @GetMapping("/adminChats/{userId}")
//    public ResponseEntity<List<ChatUser>> getAdminChatsByUserId(@PathVariable Integer userId) {
//        List<ChatUser> adminChats = chatUserService.findAdminChatsByUserId(userId);
//
//        // You can handle the case when no admin chats are found
//        if (adminChats.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(adminChats, HttpStatus.OK);
//    }
    @GetMapping("/adminChats/{userId}")
    public List<Chat> getChatsForAdminUser(@PathVariable Integer userId) {
        return chatService.getChatsForAdminUser(userId);
    }
    

    @GetMapping("/user/{userId}")
    public List<ChatUser> getChatUsersByUserId(@PathVariable Integer userId) {
        return chatUserService.findChatUsersFromUserId(userId);
    }
    
    

    @GetMapping("/publicChats")
    public List<Chat> getAllPublicChats() {
    	return chatService.findAllPublicChats();
    }

    
}
