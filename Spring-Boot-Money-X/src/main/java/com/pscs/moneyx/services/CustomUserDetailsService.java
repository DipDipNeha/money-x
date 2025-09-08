/**
 * 
 */
package com.pscs.moneyx.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.Role;
import com.pscs.moneyx.entity.UserEntity;
import com.pscs.moneyx.repo.UserRepo;

/**
 * 
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepo userRepo;

	public CustomUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userdetails = userRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found !"));

		return new User(userdetails.getUsername(), userdetails.getPassword(),
				mapRolesWithAuthority(userdetails.getRole()));
	}

	private Collection<GrantedAuthority> mapRolesWithAuthority(List<Role> roles) {

		return roles.stream().map(name -> new SimpleGrantedAuthority(name.getName())).collect(Collectors.toList());
	}

}
