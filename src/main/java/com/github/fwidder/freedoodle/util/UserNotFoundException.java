package com.github.fwidder.freedoodle.util;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String name) {
		super(name);
	}
}
