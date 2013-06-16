package com.cta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.web.dto.ExtJsAwareResult;

@Controller
@RequestMapping(value="/borrow")
public class BorrowController {

	@RequestMapping(value="/borrow", method=RequestMethod.POST)
	@ResponseBody
	public ExtJsAwareResult borrow(Long userId, Long bdId) {
		
		return ExtJsAwareResult.withData(null);
	}
	
	@RequestMapping(value="/return", method=RequestMethod.POST)
	@ResponseBody
	public ExtJsAwareResult returnBd(Long bdId) {
		
		return ExtJsAwareResult.withData(null);
	}
}
