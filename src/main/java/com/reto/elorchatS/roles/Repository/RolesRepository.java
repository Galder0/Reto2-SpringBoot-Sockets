package com.reto.elorchatS.roles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.reto.elorchatS.roles.model.Role;

public interface RolesRepository  extends CrudRepository<Role, Integer> {

	  @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
	  List<Role> getRolesByUserId(@Param("userId") Integer userId);
	
}
