package com.cta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EchoController {

	@RequestMapping("/echo")
	@ResponseBody
	public String echo() {
		return "I'm alive !! ALIVE !!";
	}
}
