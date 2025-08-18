package com.pscs.moneyx.services;

import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.CustomerLogin;
import com.pscs.moneyx.repo.CustomerLoginRepo;

@Service
public class CustomerLoginService {
	private final CustomerLoginRepo customerLoginRepo;

	public CustomerLoginService(CustomerLoginRepo customerLoginRepo) {
		this.customerLoginRepo = customerLoginRepo;
	}

	// find by username
	public CustomerLogin login(CustomerLogin customerLogin) {
		CustomerLogin customerlogin = customerLoginRepo.findByUsername(customerLogin.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("No Data Found By this Username: " + customerLogin.getUsername()));
		if (customerlogin != null) {
			 customerlogin = customerLoginRepo.findByUsernamePassword(customerLogin.getPassword(), customerlogin.getPassword());
			
		}
		return customerlogin;
	}

	public void logout() {
		// TODO Auto-generated method stub
		
	}

	public CustomerLogin getLoggedInUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerLogin createProfile(CustomerLogin customer) {
		
		CustomerLogin customerLogin = customerLoginRepo.save(customer);
		return customerLogin;
	}

}
