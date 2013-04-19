package br.gov.frameworkdemoiselle.restriction.custom.models;

import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;
	
	public DataTableLazyModel(EntityManager em) {
		super(em);
	}

	
	

}
