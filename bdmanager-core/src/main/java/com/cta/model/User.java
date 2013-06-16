package com.cta.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Setter
@Getter
public class User extends Model {

	protected String name;
}
