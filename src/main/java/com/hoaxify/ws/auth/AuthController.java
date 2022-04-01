package com.hoaxify.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;

@RestController
public class AuthController {
	
	//private static final Logger log = LoggerFactory.getLogger(AuthController.class);
//	
	@Autowired
	UserRepository userRepository;
		
	@PostMapping("/api/1.0/auth")
	@JsonView(Views.Base.class)
	ResponseEntity<?> handleAuthentication(@CurrentUser User user) {
//	ResponseEntity<?> handleAuthentication(Authentication authentication) {		
//		User user = (User) authentication.getPrincipal();
		
		return ResponseEntity.ok(user);
	}
	
}