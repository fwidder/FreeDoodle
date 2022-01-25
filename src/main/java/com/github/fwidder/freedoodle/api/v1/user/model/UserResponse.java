package com.github.fwidder.freedoodle.api.v1.user.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class UserResponse {
	
	private Collection<? extends GrantedAuthority> authorities;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	public UserResponse(@NotNull UserDetails userDetails) {
		authorities = userDetails.getAuthorities();
		username = userDetails.getUsername();
		accountNonExpired = userDetails.isAccountNonExpired();
		accountNonLocked = userDetails.isAccountNonLocked();
		credentialsNonExpired = userDetails.isCredentialsNonExpired();
		enabled = userDetails.isEnabled();
	}
}
