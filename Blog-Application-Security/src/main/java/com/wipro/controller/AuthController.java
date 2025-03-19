package com.wipro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.entity.UserEntity;
import com.wipro.model.JWTRequest;
import com.wipro.model.JWTResponse;
import com.wipro.security.JWTHelper;
import com.wipro.service.UserService;



@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JWTHelper helper;
	
	@Autowired
	private UserService userService;

	
	@PostMapping("/login")
	public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
	    this.doAuthenticate(request.getUsername(), request.getPassword());

	    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	    String token = this.helper.generateToken(userDetails);

	    System.out.println("Generated Token: " + token); // Debugging

	    JWTResponse response = new JWTResponse(token, userDetails.getUsername());

	    return ResponseEntity.ok(response);
	}


	private void doAuthenticate(String email, String password) {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e)
		{
			throw new BadCredentialsException("Invalid Username or Password!");
		}
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler()
	{
		return "Invalid Credentials!!";
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
	    UserEntity savedUser = userService.registerUser(user);
	    return ResponseEntity.ok(savedUser);
	}


}