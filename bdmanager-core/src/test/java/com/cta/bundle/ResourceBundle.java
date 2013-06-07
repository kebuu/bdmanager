package com.cta.bundle;


import java.util.ListResourceBundle;

public class ResourceBundle extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
	             {"OkKey", "OK"},
	             {"CancelKey", "Cancel"}
	        };

	}
}
