package com.cta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.service.CrudService;

@Controller
public class CrudController {

	@Autowired
	protected CrudService crudService;
	
	@RequestMapping(value="/crud/{resource}", method=RequestMethod.GET)
	@ResponseBody
	public List<Object> list(@PathVariable("resource") String resourceName) {
		return crudService.list(resourceName);
	}
	
	@RequestMapping(value="/crud/{resource}/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable("resource") String resourceName, @PathVariable("id") Long id) {
		return crudService.get(resourceName, id);
	}
	
	@RequestMapping(value="/crud/{resource}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Long> create(@PathVariable("resource") String resourceName, @RequestBody String requestBody) {
		Map<String, Long> result = new HashMap<String, Long>();
		result.put("id", crudService.create(resourceName, requestBody));
		return result;
	}

	@RequestMapping(value="/crud/{resource}", method=RequestMethod.PUT)
	@ResponseBody
	public boolean update(@PathVariable("resource") String resourceName,  @RequestBody String requestBody) {
		return crudService.update(resourceName, requestBody);
	}
	
	@RequestMapping(value="/crud/{resource}/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable("resource") String resourceName, @PathVariable("id") Long id) {
		return crudService.delete(resourceName, id);
	}
}
