package com.example.Spring4Shell.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring4Shell.security.Authorization;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class MainController {
	
	@RequestMapping({"/login/*"})
	public String login(HttpServletResponse response,Authentication authentication) {
				
		String role = "guest";
				
		if(authentication!=null &&"[ROLE_ADMIN]".equals(authentication.getAuthorities().toString())) {              
            role = "admin";            
        }
		
		//generate jwt token for role
		String jwt_token = Jwts.builder().setIssuer("aeifkz")
		.setSubject("authorization")
		.claim("role", role)		
		.signWith(			
			SignatureAlgorithm.ES256,
			Authorization.keyPair.getPrivate()
		).compact();
		
		System.out.println("create jwt-token and set cookie: " + jwt_token );
		
		Cookie cookie = new Cookie("jwt-token",jwt_token);		
		cookie.setPath("/Spring4Shell");
		response.addCookie(cookie);
		
		System.out.println("login with role:" + role);
		
		response.sendRedirect("/Spring4Shell/main");
		return null;
		
	}

	@RequestMapping({"/main"})
	public String main(HttpServletResponse response,Authentication authentication) {
		return "<html><body>" +
        			"Welcome <br/>" +
        			"Only admin can Search User....  <a href=\"/Spring4Shell/user?name=aeifkz\">Click me</a>" +
        	   "</body></html>";
	}

	@RequestMapping({"/user"})
	public String query_user(User user) {	
		
		System.out.println("query user with name:" + user.getName());
		
		return "<html><body>" +
    			"Welcome admin <br/>" +
    			"My name is " + user.getName() + "<br/>" +            			
    	   "</body></html>";
	}
	
	@RequestMapping("/*")
    public String handleError() {        
        return "404 not found";
    }

}
