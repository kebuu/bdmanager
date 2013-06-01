package com.cta.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.search.dao.SearchEngineDao;

@Controller
@RequestMapping(value="/search-engine")
public class SearchEngineController {

	@Autowired
	protected SearchEngineDao searchEngineDao;
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public String searchByQuery(@RequestBody String query) {
		return searchEngineDao.search(query);
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> createIndex(@PathVariable("name") String indexName, @RequestBody String jsonConfig) {
		searchEngineDao.createIndex(indexName, jsonConfig);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/{indexName}/{typeName}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Void> configureType(@PathVariable("indexName") String indexName, @PathVariable("typeName") String typeName, @RequestBody String typeMapping) {
		searchEngineDao.configureTypeMapping(indexName, typeName, typeMapping);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
