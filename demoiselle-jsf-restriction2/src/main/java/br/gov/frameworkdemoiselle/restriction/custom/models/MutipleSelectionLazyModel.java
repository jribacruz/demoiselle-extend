package br.gov.frameworkdemoiselle.restriction.custom.models;

import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class MutipleSelectionLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	public MutipleSelectionLazyModel(EntityManager em) {
		super(em);
	}

}
