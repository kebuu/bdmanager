package com.cta.batch.writer;

import java.util.List;

import lombok.Setter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.batch.item.ItemWriter;

import com.cta.batch.model.BdFromCsv;
import com.cta.model.Serie;

@Setter
public class SerieWriter implements ItemWriter<BdFromCsv> {

	protected SessionFactory sessionFactory;
	
    @Override
    @SuppressWarnings("unchecked")
	public void write(List<? extends BdFromCsv> items) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            List<String> serieNames = session.createCriteria(Serie.class).setProjection(Projections.property("name")).list();
            
            for (BdFromCsv bdFromCsv : items) {
                String serieName = bdFromCsv.getSerie();
                if(!serieNames.contains(serieName)) {
                    Serie newSerie = new Serie();
                    newSerie.setName(serieName);
                    session.save(newSerie);
                    serieNames.add(serieName);
                }
            }
            session.getTransaction().commit();
        } finally {
            if(session != null) {
                session.close();
            }
        }
	}
}
