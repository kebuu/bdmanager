package com.cta.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.cta.constants.Operator;
import com.cta.dto.search.SearchCriteria;
import com.cta.service.SearchService;
import com.cta.utils.MyEnumUtils;

@Controller
@RequestMapping(value="/search")
public class SearchController {

	protected static final String _OPERATOR_PROPERTY_SUFFIX = "_op_";
	@Autowired
	protected SearchService searchService;
	
	@RequestMapping(value="/{resource}", method=RequestMethod.GET)
	@ResponseBody
	public List<Object> list(@PathVariable("resource") String resourceName, WebRequest request) {
		List<SearchCriteria> criterias = transformParametersToCriterias(request);
		return searchService.getResources(resourceName, criterias);
	}
	
	@RequestMapping(value="/{resource}/unique", method=RequestMethod.GET)
	@ResponseBody
	public Object unique(@PathVariable("resource") String resourceName, WebRequest request) {
		List<SearchCriteria> criterias = transformParametersToCriterias(request);
		return searchService.getResource(resourceName, criterias);
	}
	
	private List<SearchCriteria> transformParametersToCriterias(WebRequest request) {
		List<SearchCriteria> criterias = new ArrayList<>();
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String propertyName = entry.getKey();
			String propertyValue = entry.getValue()[0];
			
			if(!propertyName.endsWith(_OPERATOR_PROPERTY_SUFFIX)) {
				SearchCriteria searchCriteria = new SearchCriteria(propertyName, Operator.EQ, propertyValue);
				
				String[] operatorStrings = parameterMap.get(propertyName + _OPERATOR_PROPERTY_SUFFIX);
				if(operatorStrings != null) {
					Operator operator = MyEnumUtils.getEnum(Operator.class, operatorStrings[0], Operator.EQ);
					searchCriteria.setOperator(operator);
				}
				
				criterias.add(searchCriteria);
			}
		}
		return criterias;
	}
}
