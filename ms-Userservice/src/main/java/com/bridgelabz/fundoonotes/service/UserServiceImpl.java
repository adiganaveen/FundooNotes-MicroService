package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
		Optional<User> maybeUser = userRepository.findById(id);
		return maybeUser.map(user -> userRepository.save(user.setActivationStatus(true))).orElseGet(() -> null);
	}

	@Override
	public String loginUser(User user, HttpServletRequest request) {
		Optional<User> maybeUser = userRepository.findByEmailId(user.getEmailId());
		Predicate<User> isValidPassword = existingUser -> bcryptEncoder.matches(user.getPassword(),
				existingUser.getPassword()) && existingUser.isActivationStatus();
		return maybeUser.filter(isValidPassword)
				.map(existingUser -> tokenGenerator.generateToken(String.valueOf(existingUser.getId())))
				.orElseGet(() -> null);

	}

	@Override
	public User updateUser(String token, User user, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> maybeUser = userRepository.findById(userId);
		return maybeUser.map(
				existingUser -> userRepository.save(existingUser.setEmailId(user.getEmailId()).setName(user.getName())
						.setMobileNumber(user.getMobileNumber()).setPassword(bcryptEncoder.encode(user.getPassword()))))
				.orElseGet(() -> null);
	}

	@Override
	public boolean deleteUser(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> maybeUser = userRepository.findById(userId);
		return maybeUser.map(existingUser -> {
			userRepository.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	public boolean forgotPassword(User user, HttpServletRequest request) {
		Optional<User> maybeUser = userRepository.findByEmailId(user.getEmailId());
		return maybeUser.map(existingUser -> {
			forgotpasswordEmail(request, existingUser, "/resetpassword/", "Reset password verification");
			return true;
		}).orElseGet(() -> false);
	}

	private void forgotpasswordEmail(HttpServletRequest request, User user, String domainUrl, String message) {
		String token = tokenGenerator.generateToken(String.valueOf(user.getId()));
		String forgotPasswordUrl = "http://localhost:4200/resetpassword/" + token;
		emailUtil.sendEmail("", "", forgotPasswordUrl);
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

	@Override
	public List<User> allUsers(HttpServletRequest request) {
		List<User> users = userRepository.findAll();
		if (!users.isEmpty()) {
			return users;
		}
		return null;
	}

	@Override
	public User colaborator(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<User> maybeUser = userRepository.findById(userId);
		User user = maybeUser.get();
		return user;
	}

	@Override
	public boolean store(MultipartFile file, String token) throws IOException {
		User user = userRepository.findById(tokenGenerator.verifyToken(token)).get();
		byte[] profilePicture = file.getBytes();
		if (profilePicture.length > 0) {
			user.setProfilePicture(profilePicture);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public User getFile(String token) {
		return userRepository.findById(tokenGenerator.verifyToken(token)).get();
	}

	@Override
	public User deleteFile(String token) {
		User user = userRepository.findById(tokenGenerator.verifyToken(token)).get();
		userRepository.save(user.setProfilePicture(null));
		return user;
	}
}