package com.github.fwidder.freedoodle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Users {
	@Id
	@Column( name = "username", nullable = false )
	private String username;
	
	@Column( name = "password", nullable = false )
	private String password;
	
	@Column( name = "enabled", nullable = false )
	private boolean enabled;
}
