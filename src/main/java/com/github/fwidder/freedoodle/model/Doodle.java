package com.github.fwidder.freedoodle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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
	
	@ElementCollection( fetch = FetchType.EAGER )
	private Set<LocalDate> dates;
	
	@OneToMany( fetch = FetchType.EAGER )
	private Set<Approve> approves;
}
