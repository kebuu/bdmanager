package com.cta.tools.i18n;

import java.util.Locale;

import com.cta.tools.threadlocal.AbstractThreadLocalService;

public class DefaultLocaleService extends AbstractThreadLocalService<Locale> implements LocaleService {

	@Override
	public Locale getLocale() {
		return getData();
	}
}
