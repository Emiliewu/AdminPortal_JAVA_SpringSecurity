package com.emilie.admindashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emilie.admindashboard.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
   User findByEmail(String email);
   
   List<User> findAll();

	//Optional<User> findByEmailContaining(String email);
}