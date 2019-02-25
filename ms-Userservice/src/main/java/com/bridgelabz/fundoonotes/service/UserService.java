package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoonotes.model.User;

public interface UserService {
	User register(User user, HttpServletRequest request);

	User activateUser(String token, HttpServletRequest request);

	String loginUser(User user, HttpServletRequest request);

	User updateUser(String token, User user, HttpServletRequest request);

	boolean deleteUser(String token, HttpServletRequest request);

	boolean forgotPassword(User user, HttpServletRequest request);

	User resetPassword(User user, String token, HttpServletRequest request);
	
	List<User> allUsers(HttpServletRequest request);

}
