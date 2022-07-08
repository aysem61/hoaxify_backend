package com.hoaxify.ws.user;


import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.GenericResponse;
import com.hoaxify.ws.user.vm.UserUpdateVM;
import com.hoaxify.ws.user.vm.UserVM;

@RestController
@RequestMapping("/api/1.0")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users")
	//@ResponseStatus(HttpStatus.CREATED)
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("user is created");
	}
	
	@GetMapping("/users")
	public Page<UserVM> getUsers(Pageable page, @CurrentUser User user){
		return userService.getUsers(page, user).map(UserVM::new);
	}
	
	@GetMapping("/users/{username}")
	public UserVM getUser(@PathVariable String username) {
		User user = userService.getByUsername(username);
		return new UserVM(user);
	}
	
	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	public UserVM updateUser(@Valid @RequestBody UserUpdateVM updatedUser, @PathVariable String username) {
//		if(!loggedInUser.getUsername().equals(username)) {
//			ApiError error = new ApiError(403, "Cannot change another users data", "/api/1.0/users/"+username);
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
//		}
		User user = userService.updateUser(username, updatedUser);
		return new UserVM(user);
	}
	
	@DeleteMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		
		return new GenericResponse("User is removed");
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public ApiError handleValidationException(MethodArgumentNotValidException exception) {
//		
//		ApiError error = new ApiError(400, "Validation error", "/api/1.0/users");
//
//		Map<String, String> validationErrors = new HashMap<>();
//		
//		for(FieldError fieldError: exception.getBindingResult().getFieldErrors()) {
//			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//		}
//		
//		error.setValidationErrors(validationErrors);
//		
//		return error;
//	}

	
	
}
