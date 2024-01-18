package com.reto.elorchatS.users.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.reto.elorchatS.roles.model.Role;
import com.reto.elorchatS.users.Service.UserService;
import com.reto.elorchatS.users.model.User;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
    private ChatService chatService;
	
	//TODO User Update for "register"
	
	 @GetMapping
	    public ResponseEntity<List<User>> findAllUsers() {
	        List<User> users = userService.findAllUsers();
	        return ResponseEntity.ok(users);
	    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Integer userId) {
    	User user = userService.findUserById(userId).orElseThrow(
    		() -> new ResponseStatusException(HttpStatus.NO_CONTENT)
    	);
    	System.out.println(user.toString());
    	return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }
    
    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody User newUser) {
        Integer createdUserId = userService.createUser(newUser);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/chats")
    public ResponseEntity<List<Chat>> getChatsByUserId(@PathVariable Integer userId) {
        List<Chat> chats = chatService.findChatsByUserId(userId);

        if (chats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(chats, HttpStatus.OK);
        }
    }
    
    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<Role>> getRolesByUserId(@PathVariable Integer userId) {
        List<Role> roles = userService.findRolesByUserId(userId);

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(roles);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<Integer> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userService.findUserById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update user properties with the values from the request body
            existingUser.setName(updatedUser.getName());
            existingUser.setSurnames(updatedUser.getSurnames());
            existingUser.setDNI(updatedUser.getDNI());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setDirection(updatedUser.getDirection());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setFctDual(updatedUser.getFctDual());
            existingUser.setPassword(updatedUser.getPassword());

            // Update user in the database
            User updated = userService.updateUser(existingUser);

            // Return a success code, you can customize this based on your needs
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            // User with the specified ID was not found
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/byEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

}
