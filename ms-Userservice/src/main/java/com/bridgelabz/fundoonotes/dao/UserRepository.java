package com.bridgelabz.fundoonotes.dao;

import com.bridgelabz.fundoonotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmailId(String emailId);
}
