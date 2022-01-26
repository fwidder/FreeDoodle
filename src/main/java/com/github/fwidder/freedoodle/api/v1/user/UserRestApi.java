package com.github.fwidder.freedoodle.api.v1.user;

import com.github.fwidder.freedoodle.api.v1.user.model.AddNewUserRequest;
import com.github.fwidder.freedoodle.api.v1.user.model.UpdatePasswordCurrentUserRequest;
import com.github.fwidder.freedoodle.api.v1.user.model.UserResponse;
import com.github.fwidder.freedoodle.dao.UserDAOService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@Flogger
@RestController
@RequestMapping( "/api/v1/user" )
public class UserRestApi {
	private final UserDAOService userDAOService;
	
	@Autowired
	public UserRestApi(UserDAOService userDAOService) {
		this.userDAOService = userDAOService;
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Get Information about the current User" )
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
		return ResponseEntity.ok(new UserResponse(userDAOService.findUserByPrincipal(principal)));
	}
	
	@Secured( "ROLE_ADMIN" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Get Information about the specified User" )
	@GetMapping( value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UserResponse> getUserByName(@PathVariable( "name" ) String name) {
		return ResponseEntity.ok(new UserResponse(userDAOService.findUserByName(name)));
	}
	
	@Operation( description = "Create a new User" )
	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> addNewUser(@RequestBody AddNewUserRequest body) {
		userDAOService.addUser(body.getName(), body.getPassword());
		return ResponseEntity.created(URI.create("/api/v1/user/" + body.getName())).build();
	}
	
	
	@Secured( "ROLE_ADMIN" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Delete the specified User" )
	@DeleteMapping( value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> deleteUserByName(@PathVariable( "name" ) String name) {
		userDAOService.deleteUser(name);
		return ResponseEntity.ok().build();
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Delete current User" )
	@DeleteMapping( produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> deleteCurrentUser(Principal principal) {
		userDAOService.deleteUser(principal);
		return ResponseEntity.ok().build();
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Update current Users Password" )
	@PatchMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> updatePasswordCurrentUser(@RequestBody UpdatePasswordCurrentUserRequest body, Principal principal) {
		userDAOService.updatePassword(principal, body.getOld_password(), body.getNew_password());
		return ResponseEntity.ok().build();
	}
	
}
