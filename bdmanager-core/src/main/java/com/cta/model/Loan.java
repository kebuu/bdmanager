package com.cta.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Setter
@Getter
public class Loan extends Model {

	@ManyToOne
	protected User user;
	
	@ManyToMany
	protected List<Bd> bds;
	
	protected Date startDate;
	protected Date endDate;
}
