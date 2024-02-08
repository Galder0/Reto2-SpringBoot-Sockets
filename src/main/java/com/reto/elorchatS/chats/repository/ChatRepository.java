package com.reto.elorchatS.chats.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.model.ChatDAO;

import jakarta.transaction.Transactional;


public interface ChatRepository extends CrudRepository<Chat, Integer>{
	
	@Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") Integer userId);

	//TODO native query
	
	@Modifying
    @Query(value = "INSERT INTO chat_user (chat_id, user_id) VALUES (:chatId, :userId)", nativeQuery = true)
    @Transactional
    void addUserToChat(@Param("chatId") Integer chatId, @Param("userId") Integer userId);

    // Method to remove a user from a chat
    @Modifying
    @Query(value = "DELETE FROM chat_user WHERE chat_id = :chatId AND user_id = :userId", nativeQuery = true)
    @Transactional
    void removeUserFromChat(@Param("chatId") Integer chatId, @Param("userId") Integer userId);

	Optional<Chat> findByName(String chatName);
	
	@Modifying
	@Query("SELECT c FROM Chat c JOIN ChatUser cu ON c.id = cu.chatId WHERE cu.isAdmin = true AND cu.userId = :userId")
    @Transactional
	List<Chat> findChatsByAdminAndUserId(@Param("userId") Integer userId);
	
	@Query("SELECT c FROM Chat c WHERE c.Private = false")
	List<Chat> findAllPublicChats();
}
