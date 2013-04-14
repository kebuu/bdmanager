package com.cta.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.cta.constants.Operator;

@Data
@AllArgsConstructor
public class SearchCriteria {

	protected String propertyName;
	protected Operator operator;
	protected String propertyValue;
}
