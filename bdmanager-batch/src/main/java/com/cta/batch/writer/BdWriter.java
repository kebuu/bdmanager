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
                		serie.addBd(createBd(bdFromCsv));
                	} else {
                		updateBdProperties(bd, bdFromCsv);
                	}
                	
                	if(bdFromCsv.getTome() == null) {
                		bd.setPositionInSerie(0);
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

	private void updateBdProperties(Bd bd, BdFromCsv bdFromCsv) {
		bd.setPositionInSerie(bdFromCsv.getTome());
		bd.setEditor(bdFromCsv.getEditeur());
	}

	private Bd getBdInSerie(Serie serie, String bdTitle) {
		Bd result = null;
		
		for (Bd bd : serie.getBds()) {
			if(bd.getTitle().equals(bdTitle)) {
				result = bd;
				break;
			}
		}
		return result;
	}

	private Bd createBd(BdFromCsv bdFromCsv) {
		Bd bd = new Bd();
		bd.setTitle(bdFromCsv.getTitre());
		bd.setPositionInSerie(bdFromCsv.getTome());
		bd.setEditor(bdFromCsv.getEditeur());
		return bd;
	}

	private Serie getSerieByName(List<Serie> series, String serieName) {
		Serie result = null;
		
		for (Serie serie : series) {
			if(serie.getName().equals(serieName)) {
				result = serie;
				break;
			}
		}
		return result;
	}
}
