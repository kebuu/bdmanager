package com.cta.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cta.BaseSpringTest;
import com.cta.model.Bd;
import com.cta.model.Serie;
import com.cta.utils.MyListUtils;
import com.cta.utils.MyObjectUtils;

public class HibernateConfigurationTest extends BaseSpringTest {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Test
	@Transactional
	public void testGetAllSeries() {
		Query serieQuery = sessionFactory.getCurrentSession().createQuery("from Serie");
		List<Serie> series = MyListUtils.cast(serieQuery.list(), Serie.class);
		assertEquals(5, series.size());
		
		MyObjectUtils.sortByNaturalOrId(series, true);
		
		assertEquals(4, series.get(0).getBds().size());
		assertEquals(4, series.get(1).getBds().size());
		assertEquals(3, series.get(2).getBds().size());
		assertEquals(1, series.get(3).getBds().size());
		assertEquals(4, series.get(4).getBds().size());
	}
	
	@Test
	@Transactional
	public void testOneBd() {
		Query serieQuery = sessionFactory.getCurrentSession().createQuery("FROM Bd bd WHERE bd.id = -1");
		Bd bd = (Bd) serieQuery.uniqueResult();
		assertNotNull(bd);
		assertNotNull(bd.getSerie());
		
		sessionFactory.getCurrentSession().createCriteria(Serie.class).createAlias("bds", "bds").add(Restrictions.eq("bds.title", "Hestia")).list();
	}
	
	@Test
	@Transactional
	public void testSomeCriteria() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Serie.class);
		criteria.createCriteria("bds").add(Restrictions.eq("title", "Hestia"));
		Serie leRegulateurSerie = (Serie) criteria.uniqueResult();
		assertEquals("Le régulateur", leRegulateurSerie.getName());
		
		criteria = sessionFactory.getCurrentSession().createCriteria(Serie.class);
		criteria.createCriteria("bds").add(Restrictions.eq("title", "Hestia"))
		.createCriteria("serie").add(Restrictions.eq("name", "Le régulateur"));
		leRegulateurSerie = (Serie) criteria.uniqueResult();
		assertEquals("Le régulateur", leRegulateurSerie.getName());
		
		criteria = sessionFactory.getCurrentSession().createCriteria(Bd.class);
		criteria.createCriteria("serie").add(Restrictions.eq("name", "Le régulateur"))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<?> result = criteria.list();
		assertEquals(4, result.size());
		
	}
} 