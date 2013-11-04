package com.cta.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of= {"id"})
public abstract class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Version
	protected int version; 
	
	@SneakyThrows
	public static <T extends Model> T newInstanceWithId(Class<T> clazz, Long id) {
		T newInstance = clazz.newInstance();
		newInstance.setId(id);
		return newInstance;
	}
}
