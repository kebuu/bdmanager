package com.cta.tools.threadlocal;

public interface ThreadLocalService<T> {
	
	T getData();

	void setData(T data);

	void removeData();

}