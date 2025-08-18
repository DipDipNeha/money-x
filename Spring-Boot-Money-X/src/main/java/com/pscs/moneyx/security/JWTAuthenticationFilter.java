package com.pscs.moneyx.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pscs.moneyx.services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
@Autowired
	private JWTGenerator generator;
@Autowired
	private CustomUserDetailsService customUserDetailsService;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token=getJwt(request);
		if(StringUtils.hasText(token)&& generator.validateToken(token)) {
			String username=generator.getUsernameFromJWT(token);
			UserDetails details=customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken=
					new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		}
 filterChain.doFilter(request, response);
	}
	
	private  String getJwt(HttpServletRequest httpServletRequest) {
		
		String token=httpServletRequest.getHeader("Authorization");
		if(StringUtils.hasText(token)&& token.startsWith("Bearer ")) {
			return token.substring(7);
			
			
		}else {
			return null;
		}
	}

}
