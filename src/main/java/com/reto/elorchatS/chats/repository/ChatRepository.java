package com.reto.elorchatS.chats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.reto.elorchatS.chats.model.Chat;


public interface ChatRepository extends CrudRepository<Chat, Integer>{
	
	@Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") Integer userId);

	//TODO native query
}
