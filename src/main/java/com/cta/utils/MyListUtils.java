package com.cta.utils;

import java.util.List;

public abstract class MyListUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> cast(List rawList, Class<T> clazz) {
		return rawList;
	}
}
