package com.github.fwidder.freedoodle.dao;

import com.github.fwidder.freedoodle.util.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserDAOServiceTest {
	
	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Autowired
	UserDAOService userDAOService;
	
	@BeforeEach
	void setUp() {
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findUserByPrincipal() user exists." )
	void findUserByPrincipal() {
		userDAOService.addUser("Test", "Test");
		
		UserDetails user = userDAOService.findUserByPrincipal(() -> "Test");
		
		assertThat(user.getUsername(), is("Test"));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findUserByName() user exists." )
	void findUserByName() {
		userDAOService.addUser("Test", "Test");
		
		UserDetails user = userDAOService.findUserByName("Test");
		
		assertThat(user.getUsername(), is("Test"));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findUserByName() user not exists." )
	void findUserByNameNotFound() {
		assertThat(userDetailsManager.userExists("Test"), is(false));
		
		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () -> userDAOService.findUserByName("Test"));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "addUser() new User." )
	void addUser() {
		assertThat(userDetailsManager.userExists("Test"), is(false));
		
		userDAOService.addUser("Test", "Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(true));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "addUser() existing User." )
	void addUserExists() {
		assertThat(userDetailsManager.userExists("Test"), is(false));
		
		userDAOService.addUser("Test", "Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(true));
		
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> userDAOService.addUser("Test", "Test2"));
		
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "deleteUserByName() existing User." )
	void deleteUserByName() {
		userDAOService.addUser("Test", "Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(true));
		
		userDAOService.deleteUser("Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(false));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "deleteUserByName() non existing User." )
	void deleteUserByNameNotFound() {
		assertThat(userDetailsManager.userExists("Test"), is(false));
		
		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () -> userDAOService.deleteUser("Test"));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "deleteUserByPrincipal() existing User." )
	void deleteUserByPrincipal() {
		userDAOService.addUser("Test", "Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(true));
		
		userDAOService.deleteUser(() -> "Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(false));
	}
}