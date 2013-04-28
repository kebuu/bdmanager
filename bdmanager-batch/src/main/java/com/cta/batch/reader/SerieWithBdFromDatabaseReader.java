package com.cta.batch.reader;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.cta.model.Serie;

public class SerieWithBdFromDatabaseReader implements ItemReader<Serie> {

	@Setter
	protected SessionFactory sessionFactory;
	protected AtomicInteger readCounter = new AtomicInteger(0);
	protected List<Serie> series;

	@Override
	public Serie read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (series == null) {
			loadSeries();
		}

		if(readCounter.get() == series.size()) {
			return null;
		} else {
			return series.get(readCounter.getAndIncrement());
		}
	}

	@SuppressWarnings("unchecked")
	private void loadSeries() {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			series = session.createCriteria(Serie.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
