package br.gov.frameworkdemoiselle.restriction.models;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.restriction.custom.LikeRestriction;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	private LikeRestriction<T> query;

	public DataTableLazyModel(Class<T> beanClass, JPABuilder<T> builder) {
		this.beanClass = beanClass;
		this.builder = builder;
		this.query = new LikeRestriction<T>();
	}

	public LikeRestriction<T> getQuery() {
		return query;
	}

	public void setQuery(LikeRestriction<T> query) {
		this.query = query;
	}

}
