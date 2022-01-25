package com.github.fwidder.freedoodle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Approve {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column( nullable = false )
	private String name;
	
	@ElementCollection( fetch = FetchType.EAGER )
	private Map<LocalDate, Answer> answers;
}
