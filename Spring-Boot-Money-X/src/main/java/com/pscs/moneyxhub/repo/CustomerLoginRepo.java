/**
 * 
 */
package com.pscs.moneyxhub.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.CustomerLogin;

/**
 * 
 */
@Repository
public interface CustomerLoginRepo extends JpaRepository<CustomerLogin, Long> {
	
	// Example of a custom query method:
	 Optional<CustomerLogin> findByUsername(String username);

	CustomerLogin findByUsernameAndPassword(String username, String password);
	

	
}
