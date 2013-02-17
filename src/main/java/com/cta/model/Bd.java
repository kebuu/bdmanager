package com.cta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity 
public class Bd {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Serie.class, cascade=CascadeType.ALL)
	@JoinColumn(name="serie_id", nullable=true, unique=false, updatable=true)
	@JsonBackReference
	protected Serie serie;
	
	protected String title;
}
