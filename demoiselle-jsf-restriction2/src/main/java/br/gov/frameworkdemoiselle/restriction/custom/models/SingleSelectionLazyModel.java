package br.gov.frameworkdemoiselle.restriction.custom.models;

import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class SingleSelectionLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;
	
	public SingleSelectionLazyModel(EntityManager em) {
		super(em);
	}

}
