package com.cta.batch.writer;

import java.util.List;

import lombok.Setter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemWriter;

import com.cta.batch.exception.UnknownSerieException;
import com.cta.batch.model.BdFromCsv;
import com.cta.model.Bd;
import com.cta.model.Serie;

@Setter
public class BdWriter implements ItemWriter<BdFromCsv> {

	protected SessionFactory sessionFactory;
	
    @Override
    @SuppressWarnings("unchecked")
	public void write(List<? extends BdFromCsv> items) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            List<Serie> series = session.createCriteria(Serie.class).list();
            
            for (BdFromCsv bdFromCsv : items) {
                Serie serie = getSerieByName(series, bdFromCsv.getSerie());
                
                if(serie == null) {
                	throw new UnknownSerieException();
                } else {
                	Bd bd = getBdInSerie(serie, bdFromCsv.getTitre());
                	if(bd == null) {
                		serie.getBds().add(createBd(bdFromCsv));
                	} 
                }
            }
            session.getTransaction().commit();
        } finally {
            if(session != null) {
                session.close();
            }
        }
	}

	private Bd getBdInSerie(Serie serie, String bdTitle) {
		for (Bd bd : serie.getBds()) {
			if(bd.getTitle().equals(bdTitle)) {
				return bd;
			}
		}
		return null;
	}

	private Bd createBd(BdFromCsv bdFromCsv) {
		Bd bd = new Bd();
		bd.setTitle(bdFromCsv.getTitre());
		return bd;
	}

	private Serie getSerieByName(List<Serie> series, String serieName) {
		for (Serie serie : series) {
			if(serie.getName().equals(serieName)) {
				return serie;
			}
		}
		return null;
	}
}
