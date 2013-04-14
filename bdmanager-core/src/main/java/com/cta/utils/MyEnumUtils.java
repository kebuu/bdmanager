package com.cta.utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class MyEnumUtils {

	public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName, E defaultValue) {
        E result = EnumUtils.getEnum(enumClass, StringUtils.upperCase(enumName));
        
        if(result == null) {
        	result = defaultValue;
        }
        return result;
    }
}
