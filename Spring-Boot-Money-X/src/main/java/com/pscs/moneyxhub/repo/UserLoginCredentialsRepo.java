/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pscs.moneyxhub.entity.UserLoginCredentials;

public interface UserLoginCredentialsRepo extends JpaRepository<UserLoginCredentials, String> {
	

}
