package com.cta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping("/echo")
	@ResponseBody
	public String echo() {
		return "I'm alive !! ALIVE !!";
	}
	
	@RequestMapping("/exception")
	@ResponseBody
	public String exception() {
		throw new RuntimeException("Testing exceptin");
	}
}
