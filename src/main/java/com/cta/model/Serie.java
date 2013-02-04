package com.cta.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import lombok.Data;

@Data
@Entity 
public class Serie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@OneToMany(mappedBy="serie", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderColumn(name="positionInSerie", nullable=true)
	protected List<Bd> bds;
	
	protected String name;
	protected String synopsis;
	protected Float mark;
}
