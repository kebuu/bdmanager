package com.cta.tools.converter;

import java.text.ParseException;
import java.util.Date;

import lombok.Setter;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.cta.config.AppConfig;
import com.cta.exception.AppException;

@Setter
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomDateConverter implements Converter {

	protected AppConfig appConfig;

	@Override
	public Object convert(Class type, Object value) {
		Date result = null;

		if (type.isAssignableFrom(Date.class) && value instanceof String) {
			try {
				result = DateUtils.parseDate((String) value, appConfig.getDateConverterFormats().toArray(ArrayUtils.EMPTY_STRING_ARRAY));
			} catch (ParseException e) {
				// Nothing to do here
			}
		} else {
			throw new AppException("date.converter.error");
		}

		return result;
	}
}
