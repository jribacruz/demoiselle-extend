package br.gov.frameworkdemoiselle.restriction.models;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyCriteria;
import br.gov.frameworkdemoiselle.restriction.custom.LikeRestriction;

public class DataTableLazyCriteria<T> extends DefaultLazyCriteria<T> {
	private static final long serialVersionUID = 1L;

	private LikeRestriction<T> query;

	public DataTableLazyCriteria(Class<T> beanClass, JPABuilder<T> builder) {
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
