package com.github.fwidder.freedoodle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@IdClass( AuthoritiesID.class )
public class Authorities {
	
	@Id
	@ManyToOne
	@JoinColumn( name = "username", nullable = false )
	private Users username;
	
	@Id
	@Column( name = "authority" )
	private String authority;
}
