package com.cta.tools.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Setter;

import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.InitializingBean;

@Setter
@SuppressWarnings("rawtypes")
public class CustomBeanConverter extends ConvertUtilsBean2 implements InitializingBean{

	protected Map<Class, Converter> customConverters = new HashMap<>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for (Entry<Class, Converter> converterByClass : customConverters.entrySet()) {
			register(converterByClass.getValue(), converterByClass.getKey());
		}
	}
}
