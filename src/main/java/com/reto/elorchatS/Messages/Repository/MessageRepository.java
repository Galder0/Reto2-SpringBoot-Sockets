package com.reto.elorchatS.Messages.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.reto.elorchatS.Messages.Model.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{

	List<Message> findMessagesByChatId(Integer chatId);

}
