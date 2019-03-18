package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("userValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@PostMapping("register")
	public ResponseEntity<?> register(@Validated @RequestBody User user, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("Invalid entry!!! Please enter valid details", HttpStatus.NOT_FOUND);
		} else {
			User newUser = userService.register(user, request);
			if (newUser != null) {
				return new ResponseEntity<User>(newUser, HttpStatus.OK);
			}
			return new ResponseEntity<String>("error in creating user", HttpStatus.CONFLICT);
		}
	}

	@GetMapping(value = "activationstatus/{token:.+}")
	public ResponseEntity<?> activateUser(@PathVariable("token") String token, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (userService.activateUser(token, request) != null) {
			response.sendRedirect("http://localhost:4200/login");
			return new ResponseEntity<String>("Your account has been activated Activated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
				HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "login")
	public ResponseEntity<?> loginUser(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) {
		String token = userService.loginUser(user, request);
		if (token != null) {
			response.setHeader("token", token);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Incorrect emailId or password", HttpStatus.CONFLICT);

	}

	@PutMapping(value = "update")
	public ResponseEntity<?> updateUser(@RequestHeader("token") String token, @RequestBody User user,
			HttpServletRequest request) {
		User newUser = userService.updateUser(token, user, request);
		if (newUser != null)
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
				HttpStatus.NOT_FOUND);

	}

	@DeleteMapping(value = "delete")
	public ResponseEntity<?> deleteUser(@RequestHeader("token") String token, HttpServletRequest request) {
		if (userService.deleteUser(token, request))
			return new ResponseEntity<String>("user deleted", HttpStatus.OK);
		return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
				HttpStatus.NOT_FOUND);

	}

	@PostMapping(value = "forgotpassword")
	public ResponseEntity<?> forgotpassword(@RequestBody User user, HttpServletRequest request) {
		if (userService.forgotPassword(user, request))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
				HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "resetpassword/{token:.+}")
	public ResponseEntity<?> resetpassword(@RequestBody User user, @PathVariable("token") String token,
			HttpServletRequest request) {
		if (userService.resetPassword(user, token, request) != null)
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("couldnot reset the password", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "allusers")
	public ResponseEntity<?> allUsers(HttpServletRequest request) {
		List<User> users = userService.allUsers(request);
		if (!users.isEmpty())
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		return new ResponseEntity<String>("couldnot reset the password", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "colaborator")
	public ResponseEntity<?> colaborator(@RequestHeader("token") String token, HttpServletRequest request) {
		User user = userService.colaborator(token, request);
		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		return new ResponseEntity<String>("user not found", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "verifyemail/{emailId:.+}")
	public ResponseEntity<?> verifyEmail(@RequestHeader("token") String token,@PathVariable("emailId") String email, HttpServletRequest request) {
		User newUser=userService.verifyEmail(token,email,request);
		if (newUser!=null)
			return new ResponseEntity<User>(newUser,HttpStatus.OK);
		return new ResponseEntity<String>("user not found", HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "photo/{token:.+}")
	public ResponseEntity<?> storeFile(@RequestParam("file") MultipartFile file, @PathVariable("token") String token)
			throws IOException {
		if (userService.store(file, token))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.CONFLICT);
	}
	
	@GetMapping("photo")
    public ResponseEntity<?> downloadFile(@RequestHeader("token") String token) {
        User user = userService.getFile(token);
        if(user!=null)
			return new ResponseEntity<User>(user,HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot download the image", HttpStatus.CONFLICT);
    }
	
	@DeleteMapping("photo")
    public ResponseEntity<?> deleteFile(@RequestHeader("token") String token) {
        User user = userService.deleteFile(token);
        if(user!=null)
			return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
    }
	
	@GetMapping("getcollaborateduser/{userId}")
    public ResponseEntity<?> getCollaboratedUser(@PathVariable("userId") int userId) {
        User user = userService.getCollaboratedUser(userId);
        if(user!=null)
			return new ResponseEntity<User>(user,HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
    }
	
}
