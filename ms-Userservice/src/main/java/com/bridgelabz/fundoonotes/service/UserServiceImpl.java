package com.bridgelabz.fundoonotes.service;

import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

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
		sendEmail(request, newUser, "/activationstatus/", "Fundoo Note Verification");
		return user;
	}

	@Override
	public User activateUser(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(id);
		return optional.map(user -> userRepository.save(user.setActivationStatus(true))).orElseGet(() -> null);
	}

	@Override
	public String loginUser(User user, HttpServletRequest request) {
		Predicate<User> isValidPassword = existingUser -> bcryptEncoder.matches(user.getPassword(),
				existingUser.getPassword()) && existingUser.isActivationStatus();

		Optional<User> maybeUser = userRepository.findByEmailId(user.getEmailId());
		return maybeUser.filter(isValidPassword)
				.map(existingUser -> tokenGenerator.generateToken(String.valueOf(existingUser.getId())))
				.orElseGet(() -> null);

	}

	@Override
	public User updateUser(String token, User user, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(userId);
		return optional
				.map(newUser -> userRepository.save(newUser.setEmailId(user.getEmailId()).setName(user.getName())
						.setMobileNumber(user.getMobileNumber()).setPassword(bcryptEncoder.encode(user.getPassword()))))
				.orElseGet(() -> null);
	}

	@Override
	public boolean deleteUser(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isPresent()) {
			User newUser = optional.get();
			userRepository.delete(newUser);
			return true;
		}
		return false;
	}

	public boolean forgotPassword(String emailId, HttpServletRequest request) {
		Optional<User> maybeUser = userRepository.findByEmailId(emailId);
		return maybeUser.map(user -> {
			sendEmail(request, user, "/resetpassword/", "Reset password verification");
			return true;
		}).orElseGet(() -> false);
	}

	private void sendEmail(HttpServletRequest request, User user, String domainUrl, String message) {
		String token = tokenGenerator.generateToken(String.valueOf(user.getId()));
		StringBuffer requestUrl = request.getRequestURL();
		String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
		activationUrl += domainUrl + token;
		emailUtil.sendEmail("", message, activationUrl);
	}

	public User resetPassword(User user, String token, HttpServletRequest request) {
		Optional<User> maybeUser = userRepository.findById(tokenGenerator.verifyToken(token));
		return maybeUser.map(
				existingUser -> userRepository.save(existingUser.setPassword(bcryptEncoder.encode(user.getPassword()))))
				.orElseGet(() -> null);
	}

}