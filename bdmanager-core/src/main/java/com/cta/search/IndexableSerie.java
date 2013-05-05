package com.cta.search;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.cta.model.Serie;

@Data
@NoArgsConstructor
public class IndexableSerie {

	protected Long id;
	protected String name;
	protected String synopsis;
	protected Float mark;
	
	public IndexableSerie(Serie serie) {
		id = serie.getId();
		name = serie.getName();
		synopsis = serie.getSynopsis();
		mark = serie.getMark();
	}
}
