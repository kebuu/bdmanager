package com.cta.bundle;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ResourceBundleTest {

	@Test
	public void test() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasenames("com.cta.bundle.ResourceBundle");
		resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
		resourceBundleMessageSource.setFallbackToSystemLocale(false);
		
		assertEquals("Cancel", resourceBundleMessageSource.getMessage("CancelKey", null, Locale.ENGLISH));
		assertEquals("Ca roule", resourceBundleMessageSource.getMessage("OkKey", null, Locale.FRENCH));
		assertEquals("OK", resourceBundleMessageSource.getMessage("OkKey", null, Locale.ENGLISH));
	}
}
