package com.github.fwidder.freedoodle.dao;

import com.github.fwidder.freedoodle.repositories.DoodleRepository;
import com.github.fwidder.freedoodle.util.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoodleDAOServiceTest {
	
	@Autowired
	DoodleDAOService doodleDAOService;
	
	@Autowired
	DoodleRepository doodleRepository;
	
	@Autowired
	UserDAOService userDAOService;
	
	@Test
	@DirtiesContext
	@DisplayName("createDoodle() new Doodle.")
	void createDoodle() {
		userDAOService.addUser("Test","Test");
		long nr = doodleRepository.count();
		
		doodleDAOService.createDoodle("Test", "Test", Collections.singletonList(LocalDate.now()), () -> "Test");
		
		assertThat(doodleRepository.count(), is(nr + 1));
	}
	@Test
	@DirtiesContext
	@DisplayName("createDoodle() invalid Doodle.")
	void createDoodleNoDate() {
		userDAOService.addUser("Test","Test");
		long nr = doodleRepository.count();
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> doodleDAOService.createDoodle("Test", "Test", Collections.emptyList(), () -> "Test"));
	}
}