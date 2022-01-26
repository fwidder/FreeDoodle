package com.github.fwidder.freedoodle.dao;

import com.github.fwidder.freedoodle.model.Answer;
import com.github.fwidder.freedoodle.model.Approve;
import com.github.fwidder.freedoodle.model.Doodle;
import com.github.fwidder.freedoodle.repositories.ApproveRepository;
import com.github.fwidder.freedoodle.repositories.DoodleRepository;
import com.github.fwidder.freedoodle.util.Assert;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Flogger
@Service
public class DoodleDAOService {
	private final ApproveRepository approveRepository;
	private final DoodleRepository doodleRepository;
	private final UserDAOService userDAOService;
	
	@Autowired
	public DoodleDAOService(ApproveRepository approveRepository, DoodleRepository doodleRepository, UserDAOService userDAOService) {
		this.approveRepository = approveRepository;
		this.doodleRepository = doodleRepository;
		this.userDAOService = userDAOService;
	}
	
	public Doodle createDoodle(String name, String description, Set<LocalDate> dates, Principal principal) {
		Assert.notBlank(name, "Name");
		Assert.notBlank(name, "Description");
		Assert.notEmpty(dates, "Date List");
		Assert.notNull(principal, "Principal");
		
		Doodle doodle = Doodle.builder()
				.creator(userDAOService.findUserByPrincipal(principal).getUsername())
				.dates(dates)
				.description(description)
				.name(name)
				.build();
		
		return doodleRepository.save(doodle);
	}
	
	public void deleteDoodle(Long id) {
		Assert.notNull(id, "Doodle-ID");
		
		Doodle doodle = doodleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Doodle with id does not exist: " + id));
		
		approveRepository.deleteAll(doodle.getApproves());
		
		doodleRepository.delete(doodle);
	}
	
	public List<Doodle> findDoodlesByName(String name) {
		return doodleRepository.findAllByName(name);
	}
	
	public List<Doodle> findDoodlesByPrincipal(Principal creator) {
		return findDoodlesByCreator(creator.getName());
	}
	
	public List<Doodle> findDoodlesByCreator(String creator) {
		return doodleRepository.findAllByCreator(creator);
	}
	
	public void approveDoodle(Long id, LocalDate date, Answer answer, Principal principal) {
		Doodle doodle = findDoodleById(id);
		UserDetails user = userDAOService.findUserByPrincipal(principal);
		
		if( ! doodle.getDates().contains(date) )
			throw new IllegalArgumentException("Date " + date + " is not available for  Doodle with id " + id);
		
		Approve approve = getApproveForUser(doodle, user);
		
		approve.getAnswers().put(date, answer);
		
		approveRepository.save(approve);
		doodleRepository.save(doodle);
		
	}
	
	public Doodle findDoodleById(Long id) {
		return doodleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Doodle with id does not exist: " + id));
	}
	
	private Approve getApproveForUser(Doodle doodle, UserDetails user) {
		Approve approve;
		List<Approve> approveList = doodle.getApproves().stream().filter(a -> a.getName().equals(user.getUsername())).toList();
		if( approveList.size() > 1 )
			throw new IllegalArgumentException("Error on Fetch approves: Found " + approveList.size() + " approves for user " + user.getUsername() + " and doodle " + doodle.getId());
		else if( approveList.size() == 1 )
			approve = approveList.get(0);
		else {
			approve = Approve
					.builder()
					.name(user.getUsername())
					.answers(new HashMap<>())
					.build();
			doodle.getApproves().add(approve);
		}
		return approve;
	}
	
	public List<Doodle> findAll() {
		return doodleRepository.findAll();
	}
	
}
