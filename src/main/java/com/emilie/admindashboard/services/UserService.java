package com.emilie.admindashboard.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.emilie.admindashboard.models.Role;
import com.emilie.admindashboard.models.User;
import com.emilie.admindashboard.repositories.RoleRepository;
import com.emilie.admindashboard.repositories.UserRepository;


@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    // super admin
    public void saveUserWithSuperAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN_SUPER"));
        userRepository.save(user);
    } 
    
    // 3
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // find all users
    public List<User> findAllUsers() {
    	return userRepository.findAll();
    }
    
    // check if there is an admin user in database
    public boolean adminexists() {
    	List<User> users = findAllUsers();
    	for(User user: users) {
    		for(Role r: user.getRoles()) {  			
    			if(r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_ADMIN_SUPER")) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    // check if the user is admin
    public boolean userisadmin(User user) {
    	for(Role r: user.getRoles()) {
    		if(r.getName().equals("ROLE_ADMIN")) {
        		return true;
        	} 
    	}
    	return false;
    }
    
    // delete a user
    public void deleteuser(Long id) {
    	userRepository.deleteById(id);
    }
    
    // assign a user as admin
    public void assignuseradmin(Long userid) {
    	User user = userRepository.findById(userid).orElse(null);
    	List<Role> roles = user.getRoles();
    	List<Role> admins = roleRepository.findByName("ROLE_ADMIN");
    	for(Role r: admins) {   		
    		roles.add(r);
    	}
    	userRepository.save(user);
    }
}
