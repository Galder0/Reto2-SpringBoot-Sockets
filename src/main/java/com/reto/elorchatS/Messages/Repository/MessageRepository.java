package com.reto.elorchatS.Messages.Repository;

import org.springframework.data.repository.CrudRepository;

import com.reto.elorchatS.Messages.Model.Message;

public interface MessageRepository extends CrudRepository<Message, Integer>{

}
