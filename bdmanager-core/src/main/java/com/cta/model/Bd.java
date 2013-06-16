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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity 
@ToString(exclude="serie")
@Setter
@Getter
public class Bd extends Model {

	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Serie.class, cascade=CascadeType.ALL)
	@JoinColumn(name="serie_id", nullable=true, updatable=true)
	@JsonBackReference
	protected Serie serie;
	
	@Formula(	"SELECT count(*) " +
				"FROM LOAN loan " +
				"JOIN LOAN_BDS loan_bds " +
					"ON loan.id = loan_bds.loan " +
				"WHERE loan.end_date is null " +
					"AND loan_bds.bds = id")
	@Type(type="org.hibernate.type.NumericBooleanType")
	protected boolean borrowed;
	
	protected String title;
	protected String editor;
	protected Date publicationDate;
	protected Integer positionInSerie;
	
	public Long getSerieId() {
		return serie.getId();
	}
}
