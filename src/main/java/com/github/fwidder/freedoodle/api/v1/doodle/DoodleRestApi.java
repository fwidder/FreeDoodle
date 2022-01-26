package com.github.fwidder.freedoodle.api.v1.doodle;

import com.github.fwidder.freedoodle.api.v1.doodle.model.CreateDoodleRequest;
import com.github.fwidder.freedoodle.dao.DoodleDAOService;
import com.github.fwidder.freedoodle.model.Doodle;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Flogger
@RestController
@RequestMapping( "/api/v1/doodle" )
public class DoodleRestApi {
	
	private final DoodleDAOService doodleDAOService;
	
	@Autowired
	public DoodleRestApi(DoodleDAOService doodleDAOService) {
		this.doodleDAOService = doodleDAOService;
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Get a list with all Doodles" )
	@GetMapping( value = "/all", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<Doodle>> getAllDoodels() {
		List<Doodle> doodles = doodleDAOService.findAll();
		return ResponseEntity.ok(doodles);
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Get a list with Doodles created by the current User" )
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<Doodle>> getDoodelsFromCurrentUser(Principal principal) {
		List<Doodle> doodles = doodleDAOService.findDoodlesByPrincipal(principal);
		return ResponseEntity.ok(doodles);
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Get a  Doodles by ID" )
	@GetMapping( value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Doodle> getDoodelById(@PathVariable( "id" ) Long id) {
		Doodle doodles = doodleDAOService.findDoodleById(id);
		return ResponseEntity.ok(doodles);
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Get Doodles by Name" )
	@GetMapping( value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<Doodle>> getDoodelsByName(@PathVariable( "name" ) String name) {
		List<Doodle> doodles = doodleDAOService.findDoodlesByName(name);
		return ResponseEntity.ok(doodles);
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Create a new Doodle" )
	@PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Doodle> createDoodle(@RequestBody CreateDoodleRequest body, Principal principal) {
		Doodle doodle = doodleDAOService.createDoodle(body.getName(), body.getDescription(), body.getDates(), principal);
		return ResponseEntity.ok(doodle);
	}
}
