package com.cta.web.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.cta.model.Serie;

@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value="/echo", method=RequestMethod.GET)
	@ResponseBody
	public String echo() {
		return "I'm alive !! ALIVE !!";
	}
	
	@RequestMapping(value="/params", method=RequestMethod.POST)
	@ResponseBody
	public String params(String param1, Long param2, @RequestBody Serie serie, WebRequest webRequest, HttpServletRequest request) {
		log.info(request.getParameterMap().toString());
		log.info("I found param1=" + param1 + " and param2=" + param2 + " and serie="+serie);
		return "I logged some stuff ! See log file";
	}
	
	@RequestMapping(value="/exception", method=RequestMethod.GET)
	@ResponseBody
	public String exception() {
		throw new RuntimeException("Testing exceptin");
	}
}
