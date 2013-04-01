package com.cta.dto.crud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrudResult {

	public enum CrudOperation {
		CREATE, UPDATE, DELETE
	}
	
	protected CrudOperation operation;
	protected boolean ok = true;
	protected String message;
	protected Object data;
}
