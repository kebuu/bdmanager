package com.cta.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.dto.crud.CrudResult;
import com.cta.service.CrudService;

@Controller
@RequestMapping(value="/crud")
public class CrudController {

	@Autowired
	protected CrudService crudService;
	
	@RequestMapping(value="/{resource}", method=RequestMethod.GET)
	@ResponseBody
	public List<Object> list(@PathVariable("resource") String resourceName) {
		return crudService.list(resourceName);
	}
	
	@RequestMapping(value="/{resource}/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable("resource") String resourceName, @PathVariable("id") Long id) {
		return crudService.get(resourceName, id);
	}
	
	@RequestMapping(value="/{resource}", method=RequestMethod.POST)
	@ResponseBody
	public CrudResult create(@PathVariable("resource") String resourceName, @RequestBody String requestBody) {
		return crudService.create(resourceName, requestBody);
	}

	@RequestMapping(value="/{resource}/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public CrudResult update(@PathVariable("resource") String resourceName, @PathVariable("id") Long id, @RequestBody String requestBody) {
		return crudService.update(resourceName, id, requestBody);
	}
	
	@RequestMapping(value="/{resource}/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public CrudResult delete(@PathVariable("resource") String resourceName, @PathVariable("id") Long id) {
		return crudService.delete(resourceName, id);
	}
}
