package com.cta.dao.impl;

import lombok.Setter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Setter
public abstract class AbstractDao {

	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}