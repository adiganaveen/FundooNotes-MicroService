package com.bridgelabz.fundoonotes.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoonotes.model.User;

public interface UserService {
	User register(User user, HttpServletRequest request);

	User activateUser(String token, HttpServletRequest request);

	String loginUser(User user, HttpServletRequest request);

	User updateUser(String token, User user, HttpServletRequest request);

	User deleteUser(String token, HttpServletRequest request);

	boolean forgotPassword(String emailId, HttpServletRequest request);

	User resetPassword(User user, String token, HttpServletRequest request);

}
