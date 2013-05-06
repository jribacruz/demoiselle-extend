package br.gov.frameworkdemoiselle.restriction.models;

import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	public DataTableLazyModel(ModelContext<T> context, EntityManager em) {
		super();
		this.context = context;
		this.em = em;
	}

}
