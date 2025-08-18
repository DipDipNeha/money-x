/**
 * 
 */
package com.pscs.moneyx.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.CustomerLogin;

/**
 * 
 */
@Repository
public interface CustomerLoginRepo extends JpaRepository<CustomerLogin, Long> {
	
	// Example of a custom query method:
	 Optional<CustomerLogin> findByUsername(String username);

	CustomerLogin findByUsernamePassword(String username, String password);
	

	
}
