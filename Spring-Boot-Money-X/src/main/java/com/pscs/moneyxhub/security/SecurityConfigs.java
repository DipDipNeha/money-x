package com.pscs.moneyxhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pscs.moneyxhub.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
	
	private JwtAuthEntryPoint authEntryPoint;
	private CustomUserDetailsService customUserDetailsService;
	
	public SecurityConfigs(CustomUserDetailsService customUserDetailsService ,JwtAuthEntryPoint authEntryPoint) {
		this.authEntryPoint=authEntryPoint;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
		.csrf(csrf->csrf.disable())
		.exceptionHandling()
		.authenticationEntryPoint(authEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**", "/sms/callback","/api/v1/license/generate","api/customer/uploadImage","/v1/webhook")
	            .permitAll() 
	            .anyRequest().authenticated())
		.httpBasic();
		httpSecurity.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
		
	}
	
//	note this is for static user configuration 
//	@Bean
//	public UserDetailsService detailsService() {
//		
//		UserDetails userDetails = User.builder().username("Dipak")
//		.password("password")
//		.roles("Admin")
//		.build();
//		UserDetails userDetails2 = User.builder().username("Dipak1")
//		.password("password1")
//		.roles("USER")
//		.build();
//		
//		return new InMemoryUserDetailsManager(userDetails,userDetails2);
//		
//	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	PasswordEncoder encoder() {
		return new  BCryptPasswordEncoder();
	}
	
	@Bean 
	public JWTAuthenticationFilter  authenticationFilter() {
		return new JWTAuthenticationFilter();
	}
	
	
}
