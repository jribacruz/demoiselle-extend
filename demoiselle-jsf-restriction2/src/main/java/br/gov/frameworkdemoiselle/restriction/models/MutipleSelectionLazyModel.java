package br.gov.frameworkdemoiselle.restriction.models;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class MutipleSelectionLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;
	
	public MutipleSelectionLazyModel(Class<T> beanClass, JPABuilder<T> builder) {
		this.beanClass = beanClass;
		this.builder = builder;
	}
}
