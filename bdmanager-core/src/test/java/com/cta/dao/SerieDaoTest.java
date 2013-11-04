package com.cta.dao;


import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cta.model.Serie;
import com.cta.test.BaseSpringTest;

public class SerieDaoTest extends BaseSpringTest {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Test
	@Transactional
	public void addSerie() {
		Serie serie = new Serie();
		serie.setName("MySerie");
		sessionFactory.getCurrentSession().save(serie);
		sessionFactory.getCurrentSession().flush();
	}
} 
