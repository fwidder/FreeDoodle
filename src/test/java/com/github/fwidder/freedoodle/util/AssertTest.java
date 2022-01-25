package com.github.fwidder.freedoodle.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AssertTest {
	
	@ParameterizedTest
	@ValueSource( strings = { " ", "\t", "     " } )
	@DisplayName( "notBlank() exception on blank input." )
	void notBlankException(String string) {
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> Assert.notBlank(string, "input"));
		assertThat(ex.getMessage(), is("input is not allowed to be Blank."));
	}
	
	@ParameterizedTest
	@ValueSource( strings = { "Test", "0123" } )
	@DisplayName( "notBlank() no exception on non blank input." )
	void notBlank(String string) {
		Assert.notBlank(string, "input");
	}
	
	@Test
	@DisplayName( "notEmpty(String) exception on empty input." )
	void notEmptyStringException() {
		String string = "";
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> Assert.notEmpty(string, "input"));
		assertThat(ex.getMessage(), is("input is not allowed to be Empty."));
	}
	
	@ParameterizedTest
	@ValueSource( strings = { "Test", "0123" } )
	@DisplayName( "notEmpty(String) no exception on non empty input." )
	void notEmptyString(String string) {
		Assert.notEmpty(string, "input");
	}
	
	
	@Test
	@DisplayName( "notEmpty(Collection) exception on empty input." )
	void notEmptyCollectionException() {
		Collection<String> col = new ArrayList<>();
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> Assert.notEmpty(col, "input"));
		assertThat(ex.getMessage(), is("input is not allowed to be Empty."));
	}
	
	@Test
	@DisplayName( "notEmpty(Collection) no exception on non empty input." )
	void notEmptyCollection() {
		Collection<String> col = new ArrayList<>();
		col.add("Test");
		Assert.notEmpty(col, "input");
	}
	
	@Test
	@DisplayName( "notNull() exception on null input." )
	void notNullException() {
		String string = null;
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> Assert.notNull(string, "input"));
		assertThat(ex.getMessage(), is("input is not allowed to be Null."));
	}
	
	@ParameterizedTest
	@ValueSource( strings = { "Test", "0123" } )
	@DisplayName( "notNull() no exception on non null input." )
	void notNull(String string) {
		Assert.notNull(string, "input");
	}
	
}