package com.cta.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.service.LoanService;
import com.cta.web.dto.ExtJsAwareResult;

@Controller
@RequestMapping(value="/loan")
public class LoanController {

	@Autowired
	protected LoanService loanService;
	
	@RequestMapping(value="/borrow", method=RequestMethod.POST)
	@ResponseBody
	public ExtJsAwareResult borrow(Long userId, Long bdId) {
		loanService.borrow(userId, bdId);
		return ExtJsAwareResult.withData(null);
	}
	
	@RequestMapping(value="/return", method=RequestMethod.POST)
	@ResponseBody
	public ExtJsAwareResult returnBd(Long bdId) {
		loanService.returnBd(bdId);
		return ExtJsAwareResult.withData(null);
	}
}
