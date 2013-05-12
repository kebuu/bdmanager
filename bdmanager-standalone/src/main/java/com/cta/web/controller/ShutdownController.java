package com.cta.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cta.web.service.ShutdownService;

@Controller
@RequestMapping("/shutdown")
public class ShutdownController {

	@Autowired
	private ShutdownService shutdownService;

	@RequestMapping(method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void shutdown() {
		shutdownService.shutdown();
	}
}
