package com.cta;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cta.model.Serie;
import com.cta.utils.MyListUtils;

public class HibernateConfTest extends BaseSpringTest {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Test
	public void test() {
		Query serieQuery = sessionFactory.openSession().createQuery("from Serie");
		List<Serie> list = MyListUtils.cast(serieQuery.list(), Serie.class);
		assertEquals(5, list.size());
	}
} 
