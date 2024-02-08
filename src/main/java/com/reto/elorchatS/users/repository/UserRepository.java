package com.reto.elorchatS.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.reto.elorchatS.ChatsUsers.model.ChatUser;
import com.reto.elorchatS.users.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	Optional<User> findByEmail(String username);

}
