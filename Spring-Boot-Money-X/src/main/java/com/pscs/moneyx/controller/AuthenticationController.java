/**
 * 
 */
package com.pscs.moneyx.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyx.entity.Role;
import com.pscs.moneyx.entity.UserEntity;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.repo.RoleRepo;
import com.pscs.moneyx.repo.UserRepo;
import com.pscs.moneyx.security.JWTGenerator;

/**
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private UserRepo userRepo;;
	private RoleRepo roleRepo;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator generator;

	public AuthenticationController(AuthenticationManager authenticationManager, UserRepo userRepo, RoleRepo roleRepo,
			PasswordEncoder passwordEncoder, JWTGenerator generator) {

		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
		this.generator = generator;
	}

	@GetMapping("/echo")
	public ResponseEntity<String> echo() {
		System.err.println("Welcome to PSCS");
		return new ResponseEntity<>("Welcome to Spring Security!", HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseData> generateToken(@RequestBody RequestData requestBody) {
		ResponseData responseData = new ResponseData();
		UserEntity entity = new UserEntity();
		
		UserEntity userEntity = ConvertRequestUtils.convertValue(requestBody.getJbody(), UserEntity.class);
		if (userRepo.existsByUsername(userEntity.getUsername())) {
			responseData.setResponseCode("01");
			responseData.setResponseMessage("User Already Registered!");
			return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
		}

		entity.setUsername(userEntity.getUsername());
		entity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		Optional<Role> roleList = roleRepo.findByName("USER");
		Role role = null;
		if (roleList.isPresent()) {
			role = roleList.get();
		}
		entity.setRole(Collections.singletonList(role));
		userRepo.save(entity);
		responseData.setResponseCode("00");
		responseData.setResponseMessage("User Registered Successfully!");
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseData> authLogic(@RequestBody RequestData requestBody) {

		
		UserEntity userEntity = ConvertRequestUtils.convertValue(requestBody.getJbody(), UserEntity.class);

		ResponseData responseData = new ResponseData();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = generator.generateToken(authentication);
		if (token == null) {
			responseData.setResponseCode("01");
			responseData.setResponseMessage("Invalid Credentials!");
			return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
		} else {
			responseData.setResponseCode("00");
			responseData.setResponseMessage("User Logged In Successfully!");
			responseData.setResponseData(token);
		}
		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}
	
	
	
}
