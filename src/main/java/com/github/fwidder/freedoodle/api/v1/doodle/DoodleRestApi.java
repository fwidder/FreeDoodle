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
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<Doodle>> getAllDoodels() {
		List<Doodle> doodles = doodleDAOService.findAll();
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
