package br.gov.frameworkdemoiselle.restriction.models;

import br.gov.frameworkdemoiselle.restriction.DefaultLazyCriteria;
import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;

public class SingleSelectionLazyCriteria<T> extends DefaultLazyCriteria<T> {
	private static final long serialVersionUID = 1L;
	
	public SingleSelectionLazyCriteria(Class<T> beanClass, JPABuilder<T> builder) {
		this.beanClass = beanClass;
		this.builder = builder;
	}
}
