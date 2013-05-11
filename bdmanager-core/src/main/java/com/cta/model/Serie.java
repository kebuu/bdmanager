package com.cta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity 
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Serie extends Model {
	
	@OneToMany(mappedBy="serie", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JsonManagedReference
	@OrderBy("positionInSerie")
	protected List<Bd> bds = new ArrayList<Bd>();
	
	protected String name;
	protected String synopsis;
	protected Float mark;
	
	public void addBd(Bd bd) {
		bds.add(bd);
		bd.setSerie(this);
		
		if(bd.getPositionInSerie() == null) {
			bd.setPositionInSerie(bds.size() - 1);
		}
	}
}
