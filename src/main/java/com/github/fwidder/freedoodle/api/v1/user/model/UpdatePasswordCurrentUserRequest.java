package com.github.fwidder.freedoodle.api.v1.user.model;

import lombok.Data;

@Data
public class UpdatePasswordCurrentUserRequest {
	private String old_password;
	private String new_password;
}
