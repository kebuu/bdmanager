package com.cta.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Sets;

@Entity 
@Setter
@Getter
public class Loan extends Model {

	@ManyToOne
	protected User user;
	
	@ManyToMany
	protected Set<Bd> bds = Sets.newHashSet();
	
	protected Date startDate;
	protected Date endDate;
}
