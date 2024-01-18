package com.reto.elorchatS.roles.Service;

import java.util.List;

import com.reto.elorchatS.roles.model.Role;

public interface RolesService {

	List<Role> findRolesByUserId(Integer userId);

}
