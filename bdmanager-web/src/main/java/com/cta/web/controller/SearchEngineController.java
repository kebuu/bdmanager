package com.cta.web.controller;

import io.searchbox.core.Search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.search.dao.SearchEngineDao;

@Controller
@RequestMapping(value="/search-engine")
public class SearchEngineController {

	@Autowired
	protected SearchEngineDao searchEngineDao;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<Object> search() {
		Search search = new Search("");
		search.clearAllType();
		return null;
	}
}
