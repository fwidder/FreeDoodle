package com.github.fwidder.freedoodle.web;


import lombok.extern.flogger.Flogger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Flogger
@Controller
@RequestMapping( produces = MediaType.TEXT_HTML_VALUE )
public class RedirectController {
	
	@GetMapping( value = "/" )
	public RedirectView root() {
		return new RedirectView("/web/public/index");
	}
}