package com.reto.elorchatS.users.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reto.elorchatS.users.Service.UserService;
import com.reto.elorchatS.users.model.User;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
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

}
