package com.github.fwidder.freedoodle.api.v1.approve;

import com.github.fwidder.freedoodle.api.v1.approve.model.ApproveDoodleRequest;
import com.github.fwidder.freedoodle.dao.DoodleDAOService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Flogger
@RestController
@RequestMapping( "/api/v1/doodle/{id}/approve" )
public class ApproveRestApi {
	
	private final DoodleDAOService doodleDAOService;
	
	@Autowired
	public ApproveRestApi(DoodleDAOService doodleDAOService) {
		this.doodleDAOService = doodleDAOService;
	}
	
	@Secured( "ROLE_USER" )
	@ResponseStatus( HttpStatus.OK )
	@Operation( description = "Approve a Doodle" )
	@PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> approveDoodle(@PathVariable( "id" ) Long id, @RequestBody ApproveDoodleRequest body, Principal principal) {
		body.getAnswerMap().forEach((localDate, answer) -> doodleDAOService.approveDoodle(id, localDate, answer, principal));
		return ResponseEntity.ok().build();
	}
}
