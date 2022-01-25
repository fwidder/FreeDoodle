package com.github.fwidder.freedoodle.dao;

import com.github.fwidder.freedoodle.model.Answer;
import com.github.fwidder.freedoodle.repositories.ApproveRepository;
import com.github.fwidder.freedoodle.repositories.DoodleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
class DoodleDAOServiceTest {
	
	@Autowired
	DoodleDAOService doodleDAOService;
	
	@Autowired
	DoodleRepository doodleRepository;
	
	@Autowired
	ApproveRepository approveRepository;
	
	@Autowired
	UserDAOService userDAOService;
	
	@Test
	@DirtiesContext
	@DisplayName( "createDoodle() new Doodle." )
	void createDoodle() {
		userDAOService.addUser("Test", "Test");
		long nr = doodleRepository.count();
		
		doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test");
		
		assertThat(doodleRepository.count(), is(nr + 1));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "createDoodle() invalid Doodle." )
	void createDoodleNoDate() {
		userDAOService.addUser("Test", "Test");
		long nr = doodleRepository.count();
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> doodleDAOService.createDoodle("Test", "Test", Collections.emptySet(), () -> "Test"));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "deleteDoodle() existing Doodle." )
	void deleteDoodle() {
		userDAOService.addUser("Test", "Test");
		Long id = doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test").getId();
		
		doodleDAOService.deleteDoodle(id);
		assertThat(doodleRepository.count(), is(0L));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "deleteDoodle() non existing Doodle." )
	void deleteDoodleNotExists() {
		assertThat(doodleRepository.findById(999L).isEmpty(), is(true));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> doodleDAOService.deleteDoodle(999L));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findDoodlesByName() existing Doodle." )
	void findDoodlesByName() {
		userDAOService.addUser("Test", "Test");
		doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test");
		
		doodleDAOService.findDoodlesByName("Test");
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findDoodlesByPrincipal() existing Doodle." )
	void findDoodlesByPrincipal() {
		userDAOService.addUser("Test", "Test");
		doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test");
		
		doodleDAOService.findDoodlesByPrincipal(() -> "Test");
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findDoodlesByCreator() existing Doodle." )
	void findDoodlesByCreator() {
		userDAOService.addUser("Test", "Test");
		doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test");
		
		doodleDAOService.findDoodlesByName("Test");
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "approveDoodle() Doodle for wrong Date." )
	void approveWrongDateDoodle() {
		assertThat(approveRepository.count(), is(0L));
		
		userDAOService.addUser("Test", "Test");
		Long id = doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test").getId();
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> doodleDAOService.approveDoodle(id, LocalDate.now().plusDays(2), Answer.MAYBE, () -> "Test"));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "approveDoodle() additional approve Doodle." )
	void approveAdditionalDoodle() {
		assertThat(approveRepository.count(), is(0L));
		
		userDAOService.addUser("Test", "Test");
		Long id = doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test").getId();
		
		doodleDAOService.approveDoodle(id, LocalDate.now(), Answer.MAYBE, () -> "Test");
		doodleDAOService.approveDoodle(id, LocalDate.now(), Answer.YES, () -> "Test");
		
		assertThat(approveRepository.count(), is(1L));
		assertThat(approveRepository.findAll().get(0).getAnswers().get(LocalDate.now()), is(Answer.YES));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "approveDoodle() new approve Doodle." )
	void approveNewDoodle() {
		assertThat(approveRepository.count(), is(0L));
		
		userDAOService.addUser("Test", "Test");
		Long id = doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test").getId();
		
		doodleDAOService.approveDoodle(id, LocalDate.now(), Answer.MAYBE, () -> "Test");
		
		assertThat(approveRepository.count(), is(1L));
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findDoodleById() existing Doodle." )
	void findDoodleById() {
		userDAOService.addUser("Test", "Test");
		Long id = doodleDAOService.createDoodle("Test", "Test", Collections.singleton(LocalDate.now()), () -> "Test").getId();
		
		doodleDAOService.findDoodleById(id);
	}
	
	@Test
	@DirtiesContext
	@DisplayName( "findDoodleById() non existing Doodle." )
	void findDoodleByIdNotExists() {
		assertThat(doodleRepository.findById(999L).isEmpty(), is(true));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> doodleDAOService.findDoodleById(999L));
	}
}