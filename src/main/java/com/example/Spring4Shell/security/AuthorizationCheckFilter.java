package com.example.Spring4Shell.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Spring4Shell.Authorization;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class AuthorizationCheckFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (!request.getServletPath().startsWith("/login")) {
			
			String role = null;			
			String jwt = null;
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {				
				for(int i = 0; i < cookies.length; i++) {
					System.out.println("cookies[i].getName()=" + cookies[i].getName());
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
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if("admin".equals(role)) {
				filterChain.doFilter(request, response);
			}
			else {
				request.getSession().invalidate();
				response.sendRedirect("/Spring4Shell/login/");
			}
			
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
