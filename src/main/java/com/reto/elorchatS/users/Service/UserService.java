package com.reto.elorchatS.users.Service;

import java.util.List;
import java.util.Optional;

import com.reto.elorchatS.roles.model.Role;
import com.reto.elorchatS.users.model.User;

public interface UserService {

	Optional<User> findUserById(Integer userId);

	List<User> findAllUsers();

	Integer createUser(User newUser);

	List<Role> findRolesByUserId(Integer userId);

	User updateUser(User existingUser);

	Optional<User> findUserByEmail(String email);

	
	

}
