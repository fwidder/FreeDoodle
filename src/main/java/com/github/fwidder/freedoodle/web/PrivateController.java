package com.github.fwidder.freedoodle.web;

import com.github.fwidder.freedoodle.dao.DoodleDAOService;
import com.github.fwidder.freedoodle.dao.UserDAOService;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Flogger
@Controller
@RequestMapping(value = "/web/private", produces = MediaType.TEXT_HTML_VALUE)
public class PrivateController {
	private final UserDAOService userDAOService;
	private final DoodleDAOService doodleDAOService;

	@Autowired
	public PrivateController(UserDAOService userDAOService, DoodleDAOService doodleDAOService) {
		this.userDAOService = userDAOService;
		this.doodleDAOService = doodleDAOService;
	}

	@GetMapping(value = "/profile")
	public ModelAndView profile(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("profile");
		modelAndView.addObject("user", userDAOService.findUserByPrincipal(principal));
		return modelAndView;
	}

	@GetMapping(value = "/myDoodles")
	public ModelAndView myDoodles(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("myDoodles");
		modelAndView.addObject("doodles", doodleDAOService.findDoodlesByPrincipal(principal));
		return modelAndView;
	}
}
