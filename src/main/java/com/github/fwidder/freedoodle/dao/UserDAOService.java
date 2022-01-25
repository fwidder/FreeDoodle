package com.github.fwidder.freedoodle.dao;

import com.github.fwidder.freedoodle.util.Assert;
import com.github.fwidder.freedoodle.util.UserNotFoundException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Flogger
public class UserDAOService {
	
	private final UserDetailsManager userDetailsManager;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserDAOService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
		this.userDetailsManager = userDetailsManager;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserDetails findUserByPrincipal(Principal principal) {
		Assert.notNull(principal, "Principal");
		return findUserByName(principal.getName());
	}
	
	public UserDetails findUserByName(String name) {
		Assert.notBlank(name, "Name");
		if( userDetailsManager.userExists(name) )
			return userDetailsManager.loadUserByUsername(name);
		throw new UserNotFoundException(name);
	}
	
	public void addUser(String name, String password) {
		Assert.notBlank(name, "Name");
		Assert.notBlank(password, "Password");
		UserDetails user = User.withUsername(name).password(passwordEncoder.encode(password)).roles("USER").build();
		
		userDetailsManager.createUser(user);
	}
	
	public void deleteUser(Principal principal) {
		Assert.notNull(principal, "Principal");
		deleteUser(principal.getName());
	}
	
	public void deleteUser(String name) {
		Assert.notBlank(name, "Name");
		if( userDetailsManager.userExists(name) )
			userDetailsManager.deleteUser(name);
		else
			throw new UserNotFoundException(name);
	}
}
