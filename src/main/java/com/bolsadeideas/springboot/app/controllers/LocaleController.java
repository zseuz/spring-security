package com.bolsadeideas.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Controller
public class LocaleController {
	
	@Autowired
	public LocaleChangeInterceptor lang;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		
		log.info("es: "+lang);
		String ultimaUrl = request.getHeader("referer");
		return "redirect:".concat(ultimaUrl);
	}

}
