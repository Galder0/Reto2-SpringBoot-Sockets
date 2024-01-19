package com.reto.elorchatS.users.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reto.elorchatS.roles.Service.RolesService;
import com.reto.elorchatS.roles.model.Role;
import com.reto.elorchatS.users.model.User;
import com.reto.elorchatS.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolesService rolesService;

	@Override
	public Optional<User> findUserById(Integer userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	// esta es la funcion que busca al usuario por email. 
		// ya que en este caso el campo de login es el email
    	// si fuese otro, realizar otra funcion
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + username + " not found"));
	}
	
	@Override
    public Integer createUser(User newUser) {
        // You might want to add some validation or business logic here
        User createdUser = userRepository.save(newUser);
        return createdUser.getId();
    }

	@Override
	public List<Role> findRolesByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return rolesService.findRolesByUserId(userId);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public User updateUser(User existingUser) {
	    try {
	        // Update user in the database
	        User updatedUser = userRepository.save(existingUser);
	        return updatedUser;
	    } catch (Exception e) {
	        // Log the exception or handle it as needed
	        e.printStackTrace();
	        return null;  // Return null in case of an error during the update
	    }
	}

	@Override
	public boolean changePassword(String email, String oldPassword, String newPassword) {
	    Optional<User> existingUserOptional = userRepository.findByEmail(email);

	    if (existingUserOptional.isPresent()) {
	        User existingUser = existingUserOptional.get();

	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	        // Check if the old password is correct
	        if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
	            // Old password is correct, update the password
	            String hashedNewPassword = passwordEncoder.encode(newPassword);
	            existingUser.setPassword(hashedNewPassword);
	            userRepository.save(existingUser);
	            return true;
	        } else {
	            // Old password is incorrect
	            return false;
	        }
	    } else {
	        // User with the specified email not found
	        return false;
	    }
	}
	

}
