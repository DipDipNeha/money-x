package com.pscs.moneyx.services;

import java.util.Random;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.CustomerLogin;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.repo.CustomerLoginRepo;

@Service
public class CustomerLoginService {
	private final CustomerLoginRepo customerLoginRepo;

	public CustomerLoginService(CustomerLoginRepo customerLoginRepo) {
		this.customerLoginRepo = customerLoginRepo;
	}

	// find by username
	public ResponseData login(RequestData request) {
		ResponseData response = new ResponseData();
		try {

			JSONObject requestJson = new JSONObject(request.getJbody());
			CustomerLogin customerlogin = customerLoginRepo.findByUsername(requestJson.getString("username"))
					.orElseThrow(() -> new ResourceNotFoundException(
							"No Data Found By this Username: " + requestJson.getString("username")));
			if (customerlogin != null) {
				customerlogin = customerLoginRepo.findByUsernameAndPassword(requestJson.getString("username"),
						requestJson.getString("password"));

			}

			if (customerlogin == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Login Failed");

			} else {
				response.setResponseCode("00");
				response.setResponseMessage("Login Successful");
				customerlogin.setPassword(null);
				response.setResponseData(customerlogin);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}

	public ResponseData createProfile(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			JSONObject jsonObject = new JSONObject(request.getJbody());

			CustomerLogin customerLogin = new CustomerLogin();
			customerLogin.setUsername(jsonObject.getString("username"));
			customerLogin.setPassword(jsonObject.getString("password"));
			customerLogin.setAuthType(jsonObject.getString("authType"));
			customerLogin.setAuthValue(jsonObject.getString("authValue"));
			customerLogin.setCustomerType(jsonObject.getString("customerType"));
			customerLogin.setLastLogin(jsonObject.getString("lastLogin"));
			customerLogin.setCustomerId(jsonObject.getString("customerId"));
			customerLogin.setFullName(jsonObject.getString("fullName"));
			customerLogin.setAccountNumber(jsonObject.getString("accountNumber"));
			customerLogin.setEmail(jsonObject.getString("email"));
			customerLogin.setMobileNumber(jsonObject.getString("mobileNumber"));

			CustomerLogin customer = customerLoginRepo.save(customerLogin);

			if (customer == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Profile Creation Failed");

			} else {
				response.setResponseCode("00");
				response.setResponseMessage("Profile Created Successfully");
				response.setResponseData(customer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public ResponseData generateOtp(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			JSONObject jsonObject = new JSONObject(request.getJbody());
			String otp = "234567";
			// generate otp by using random

			response.setResponseCode("00");
			response.setResponseMessage("OTP Generated Successfully " + otp);
			response.setResponseData(otp);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	// create forget password method
	public ResponseData forgetPassword(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			JSONObject jsonObject = new JSONObject(request.getJbody());
			String username = jsonObject.getString("username");
			String otp = jsonObject.getString("otp");
			String email= jsonObject.getString("email");
			String oldPassword = jsonObject.getString("password");
			String newPassword = jsonObject.getString("newPassword");
			// find customer login by username
			CustomerLogin customerLogin = customerLoginRepo.findByUsernameAndPassword(username, oldPassword);
			if (customerLogin == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Invalid Username or Password");
				return response;
			} else {
				customerLogin.setPassword(newPassword);
				customerLoginRepo.save(customerLogin);

				response.setResponseCode("00");
				response.setResponseMessage("Password Updated Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to Update Password");
		}
		return response;
	}

	public void logout() {
		// TODO Auto-generated method stub

	}

	public CustomerLogin getLoggedInUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
