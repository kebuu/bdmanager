package com.cta.tools.threadlocal;


public abstract class AbstractThreadLocalService<T> implements ThreadLocalService<T> {

	protected ThreadLocal<T> dataHolder = new ThreadLocal<>();
	
	@Override
	public T getData() {
		return dataHolder.get();
	}
	
	@Override
	public void setData(T data) {
		dataHolder.set(data);
	}
	
	@Override
	public void removeData() {
		dataHolder.remove();
	}
}
