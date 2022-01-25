package com.github.fwidder.freedoodle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Doodle {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column( nullable = false )
	private String name;
	
	@Column
	private String creator;
	
	@Column
	private String description;
	
	@ElementCollection
	private List<LocalDate> dates;
	
	@OneToMany
	private List<Approve> approves;
}
