package com.cta.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cta.config.AppConfig;

@Controller
public class CrossDomainController {

	@Autowired
	protected AppConfig appConfig;
	
	@RequestMapping(value="/**",method=RequestMethod.OPTIONS)
	public ResponseEntity<Void> optionsForEveryone(HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", appConfig.getCrossDomainAllowedOrigins());
		headers.add("Access-Control-Allow-Methods", appConfig.getCrossDomainAllowedMethods());
		headers.add("Access-Control-Allow-Headers", appConfig.getCrossDomainAllowedHeaders());
		headers.add("Access-Control-Max-age", appConfig.getCrossDomainMaxAge());
		headers.add("Content-Length", "0");
		
		return new ResponseEntity<Void>(null, headers, HttpStatus.NO_CONTENT);
	}
}
