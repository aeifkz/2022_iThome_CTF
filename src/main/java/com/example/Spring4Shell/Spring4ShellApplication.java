package com.example.Spring4Shell;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication
@RestController
public class Spring4ShellApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(Spring4ShellApplication.class, args);
	}
		
	@RequestMapping({"/login/*"})
	public String login(HttpServletResponse response,Authentication authentication) {
		
		System.out.println("call login");
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
		
		Cookie cookie = new Cookie("jwt-token",jwt_token);		
		cookie.setPath("/Spring4Shell");
		response.addCookie(cookie);
		
		if(role.equals("admin")) {
			return "<html><body>" +
        			"Welcome admin <br/>" +
        			"You can Search User now... <a href=\"/Spring4Shell/user?name=aeifkz\">Click me</a>" +            			
        	   "</body></html>";
		}
		else {
			return "<html><body>" +
        			"You are the guest" +
        	   "</body></html>";
		}
		
	}

	@RequestMapping({"/user"})
	public String test(User user) {		
		return "<html><body>" +
    			"Welcome admin <br/>" +
    			"My name is " + user.getName() + "<br/>" +            			
    	   "</body></html>";
	}	


}
