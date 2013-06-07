package com.cta.bundle;
import java.util.ListResourceBundle;

public class ResourceBundle_fr extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
	             {"OkKey", "Ca roule"},
	             {"CancelKey", "Annuler"}
	        };

	}
}
