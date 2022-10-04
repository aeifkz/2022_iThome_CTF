package com.example.Spring4Shell.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class AuthorizationCheckFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("call " + request.getServletPath());
		
		if (!request.getServletPath().startsWith("/login")) {
			
			String role = null;			
			String jwt = null;
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {				
				for(int i = 0; i < cookies.length; i++) {					
					if (cookies[i].getName().equals("jwt-token")) {
						jwt = cookies[i].getValue();
						break;
					}
				}
			}
						
			// verify jwt token
			if(jwt!=null) {				
				try {
					JwtParser jwtVerifier = Jwts.parserBuilder().setSigningKey(Authorization.keyPair.getPublic()).build();
					role = jwtVerifier.parseClaimsJws(jwt).getBody().get("role", String.class);
					System.out.println("validate jwt-token with role:" + role);
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("validate jwt-token fail");
				}
			}
			
			if("admin".equals(role)) {
				filterChain.doFilter(request, response);
			}
			else {
				if(role!=null && request.getServletPath().startsWith("/main")){
					filterChain.doFilter(request, response);
				}
				else {
					System.out.println("clear session and redirect to login");
					if(request.getSession()!=null) {
						request.getSession().invalidate();
					}
					response.sendRedirect("/Spring4Shell/login/");
				}
			}
			
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
