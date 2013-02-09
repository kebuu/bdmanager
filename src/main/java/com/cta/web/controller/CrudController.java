package com.cta.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.service.CrudService;

@Controller
public class CrudController {

	@Autowired
	protected CrudService crudService;
	
	@RequestMapping("/crud/{resource}")
	@ResponseBody
	public List<Object> listResource(@PathVariable("resource") String resourceName) {
		return null;
	}
	
}
