package com.bridgelabz.fundoonotes.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dao.UserRepository;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.utility.EmailUtil;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EmailUtil emailUtil;

	@Override
	public User register(User user, HttpServletRequest request) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		User newUser = userRepository.save(user);
		if (newUser == null) {
			return null;
		}
		String token = tokenGenerator.generateToken(String.valueOf(newUser.getId()));
		StringBuffer requestUrl = request.getRequestURL();
		String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
		activationUrl = activationUrl + "/activationstatus/" + token;
		emailUtil.sendEmail("", "Fundoo Note Verification", activationUrl);
		return user;
	}

	@Override
	public User activateUser(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(id);
		return optional.map(user -> userRepository.save(user.setActivationStatus(true))).orElseGet(() -> null);
	}

	@Override
	public User loginUser(User user, HttpServletRequest request, HttpServletResponse response) {
		User verifyUser = userRepository.findByEmailId(user.getEmailId());
		if (bcryptEncoder.matches(user.getPassword(), verifyUser.getPassword()) && verifyUser.isActivationStatus()) {
			String token = tokenGenerator.generateToken(String.valueOf(verifyUser.getId()));
			response.setHeader("token", token);
			return verifyUser;
		}
		return null;
	}

	// return optional
	// .map(newUser ->
	// userRepository.save(newUser.setEmailId(user.getEmailId()).setName(user.getName())
	// .setMobileNumber(user.getMobileNumber()).setPassword(user.getPassword())))
	// .orElseGet(() -> null);

	@Override
	public User updateUser(String token, User user, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isPresent()) {
			User newUser = optional.get();
			userRepository.save(newUserUpdated(newUser, user));
			return newUser;
		}
		return null;
	}

	public User newUserUpdated(User newUser, User user) {
		if (user.getName() != null)
			newUser.setName(user.getName());
		if (user.getEmailId() != null)
			newUser.setEmailId(user.getEmailId());
		if (String.valueOf(user.getMobileNumber()) != null)
			newUser.setMobileNumber(user.getMobileNumber());
		if (user.getPassword() != null)
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return newUser;
	}

	@Override
	public User deleteUser(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isPresent()) {
			User newUser = optional.get();
			userRepository.delete(newUser);
			return newUser;
		}
		return null;
	}

	public boolean forgotPassword(String emailId, HttpServletRequest request) {
		User user = userRepository.findByEmailId(emailId);
		if (user != null) {
			String token = tokenGenerator.generateToken(String.valueOf(user.getId()));
			StringBuffer requestUrl = request.getRequestURL();
			String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			activationUrl = activationUrl + "/resetpassword/" + token;
			emailUtil.sendEmail("", "Reset password verification", activationUrl);
			return true;
		}
		return false;
	}

	public User resetPassword(User user, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(userId);
		return optional
				.map(newUser -> userRepository.save(newUser.setPassword(bcryptEncoder.encode(user.getPassword()))))
				.orElseGet(()->null);
	}

}