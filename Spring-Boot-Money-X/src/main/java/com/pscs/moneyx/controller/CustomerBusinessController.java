/**
 * 
 */
package com.pscs.moneyx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyx.entity.CustomerLogin;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.services.CustomerBusinessService;

/**
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerBusinessController {

	private final CustomerBusinessService customerLoginService;

	public CustomerBusinessController(CustomerBusinessService customerLoginService) {
		this.customerLoginService = customerLoginService;
	}

	// echo
	@GetMapping("/echo")
	public ResponseEntity<String> echo() {
		return new ResponseEntity<>("Welcome to PSCS", HttpStatus.OK);
	}

	// create profile post api by using
	@PostMapping("/register")
	public ResponseEntity<ResponseData> createProfile(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.createProfile(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// create login post api by using
	@PostMapping("/login")
	public ResponseEntity<ResponseData> login(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.login(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// generate otp
	@PostMapping("/generateotp")
	public ResponseEntity<ResponseData> generateOtp(@RequestBody RequestData requestBody) {

		// Assuming a method to generate OTP exists in the service
		ResponseData response = customerLoginService.generateOtp(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// forget password
	@PostMapping("/forgetpassword")
	public ResponseEntity<ResponseData> forgetPassword(@RequestBody RequestData requestBody){
		
		ResponseData response = customerLoginService.forgetPassword(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	

	@PostMapping("/current-user")
	public ResponseEntity<ResponseData> getCurrentUser(@RequestBody RequestData requestBody) {
		CustomerLogin loggedInUser = customerLoginService.getLoggedInUser();

		if (loggedInUser == null) {
			ResponseData response = new ResponseData();
			response.setResponseCode("01");
			response.setResponseMessage("No user is logged in");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

		ResponseData response = new ResponseData();
		response.setResponseCode("00");
		response.setResponseMessage("User retrieved successfully");
		response.setResponseData(loggedInUser);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
  //fetch country
	@PostMapping("/fetchcountry")
	public ResponseEntity<ResponseData> fetchCountry(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchCountry();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//fetch business type
	@PostMapping("/fetchbusinesstype")
	public ResponseEntity<ResponseData> fetchBusinessType(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchBusinessType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//fetch assign role
	@PostMapping("/fetchassignrole")
	public ResponseEntity<ResponseData> fetchAssignRole(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchAssignRole();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//fetch upload document type based on country
	@PostMapping("/fetchdocumenttype")
	public ResponseEntity<ResponseData> fetchDocumentType(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchDocumentType(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<ResponseData> logout() {
		customerLoginService.logout();

		ResponseData response = new ResponseData();
		response.setResponseCode("00");
		response.setResponseMessage("Logout Successful");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
