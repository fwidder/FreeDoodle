package com.github.fwidder.freedoodle.web;

import lombok.extern.flogger.Flogger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Flogger
@Controller
@RequestMapping( value = "/web/public", produces = MediaType.TEXT_HTML_VALUE )
public class PublicController {
	
	@GetMapping( value = "/index" )
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@GetMapping( value = "/login" )
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}
