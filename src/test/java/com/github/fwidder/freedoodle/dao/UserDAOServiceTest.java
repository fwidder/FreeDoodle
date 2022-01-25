package com.github.fwidder.freedoodle.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.annotation.DirtiesContext;

import java.security.Principal;

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
	void findUserByPrincipal() {
		userDAOService.addUser("Test", "Test");
		
		UserDetails user = userDAOService.findUserByPrincipal(() -> "Test");
		
		assertThat(user.getUsername(), is("Test"));
	}
	
	@Test
	@DirtiesContext
	void findUserByName() {
		userDAOService.addUser("Test", "Test");
		
		UserDetails user = userDAOService.findUserByName("Test");
		
		assertThat(user.getUsername(), is("Test"));
	}
	
	@Test
	@DirtiesContext
	void addUser() {
		assertThat(userDetailsManager.userExists("Test"), is(false));
		
		userDAOService.addUser("Test", "Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(true));
	}
	
	@Test
	@DirtiesContext
	void deleteUser() {
		userDAOService.addUser("Test", "Test");
		userDAOService.deleteUser("Test");
		
		assertThat(userDetailsManager.userExists("Test"), is(false));
	}
}