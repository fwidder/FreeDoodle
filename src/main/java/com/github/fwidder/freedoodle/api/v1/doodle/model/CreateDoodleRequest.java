package com.github.fwidder.freedoodle.api.v1.doodle.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class CreateDoodleRequest {
	private String name;
	private String description;
	private Set<LocalDate> dates;
}
