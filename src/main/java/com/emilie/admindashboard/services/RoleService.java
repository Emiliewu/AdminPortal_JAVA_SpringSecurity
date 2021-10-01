package com.emilie.admindashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emilie.admindashboard.models.Role;
import com.emilie.admindashboard.repositories.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository rolerepository;
	
	//find role
	public List<Role> findRoleByName(String name) {
		return rolerepository.findByName(name);
	}
	//all roles
	public List<Role> allRoles(){
		return rolerepository.findAll();
	}
	
}
