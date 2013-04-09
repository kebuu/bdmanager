package com.cta.service.impl;

import java.util.List;

import lombok.Setter;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import com.cta.dto.crud.validation.ValidationResult;
import com.cta.service.ValidationService;
import com.cta.tools.validator.Validator;
import com.google.common.collect.Lists;

@Setter
public class DefaultValidationService implements ValidationService {

	protected List<Validator> validators;

	@Override
	public ValidationResult validate(Object data) {
		ValidationResult result = new ValidationResult();

		List<Validator> applicableValidators = getApplicableValidators(data, validators);
		for (Validator validator : applicableValidators) {
			validator.validate(data);
		}

		return result;
	}

	private List<Validator> getApplicableValidators(final Object data, List<Validator> validators) {
		List<Validator> applicableValidators = Lists.newArrayList(validators);
		
		CollectionUtils.filter(applicableValidators, new Predicate<Validator>() {

			@Override
			public boolean evaluate(Validator validator) {
				return validator.canValidate(data.getClass());
			}

		});

		return applicableValidators;
	}
}
