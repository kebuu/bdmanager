package com.cta.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity 
@ToString(exclude="serie")
@Setter
@Getter
public class Bd extends Model{

	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Serie.class, cascade=CascadeType.ALL)
	@JoinColumn(name="serie_id", nullable=true, updatable=true)
	@JsonBackReference
	protected Serie serie;
	
	protected String title;
	protected Date publicationDate;
	protected Integer positionInSerie;
}
