package com.cta;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cta.model.Serie;
import com.cta.utils.MyListUtils;

@RunWith(SpringJUnit4ClassRunner.class)
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
