package com.reto.elorchatS.roles.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto.elorchatS.roles.Repository.RolesRepository;
import com.reto.elorchatS.roles.model.Role;

@Service
public class RolesServiceImpl implements RolesService{

	@Autowired
	RolesRepository rolesRepository;
	
	@Override
	public List<Role> findRolesByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return rolesRepository.getRolesByUserId(userId);
	}

}
