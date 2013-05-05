package com.cta.search;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.cta.model.Bd;

@Data
@NoArgsConstructor
public class IndexableBd {

	protected Long id;
	protected String title;
	protected Date publicationDate;
	protected Integer positionInSerie;
	
	public IndexableBd(Bd bd) {
		id = bd.getId();
		title = bd.getTitle();
		publicationDate = bd.getPublicationDate();
		positionInSerie = bd.getPositionInSerie();
	}
	
}
