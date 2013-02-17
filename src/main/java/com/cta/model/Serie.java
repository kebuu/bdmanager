package com.cta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
public class Serie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@OneToMany(mappedBy="serie", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderColumn(name="positionInSerie", nullable=false)
	@Fetch(FetchMode.JOIN)
	@JsonManagedReference
	protected List<Bd> bds = new ArrayList<Bd>();
	
	protected String name;
	protected String synopsis;
	protected Float mark;
}
