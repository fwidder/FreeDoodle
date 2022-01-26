package com.github.fwidder.freedoodle.api.v1.user.model;

import lombok.Data;

@Data
public class CreateUserRequest {
	private String name;
	private String password;
}
