package com.cta.web.swagger;

import java.util.List;

import org.springframework.web.context.request.WebRequest;

import com.mangofactory.swagger.configuration.ExtensibilityModule;
import com.mangofactory.swagger.models.IgnorableTypeRule;
import com.mangofactory.swagger.models.TypeProcessingRule;

public class CustomExtensibilityModule extends ExtensibilityModule {

	@Override
	protected void customizeTypeProcessingRules(List<TypeProcessingRule> typeProcessingRules) {
		typeProcessingRules.add(IgnorableTypeRule.ignorable(WebRequest.class));
	}
}
