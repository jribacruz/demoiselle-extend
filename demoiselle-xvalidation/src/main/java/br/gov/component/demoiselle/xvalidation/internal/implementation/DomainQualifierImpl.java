package br.gov.component.demoiselle.xvalidation.internal.implementation;

import javax.enterprise.util.AnnotationLiteral;

import br.gov.component.demoiselle.xvalidation.internal.qualifier.Domain;

@SuppressWarnings("all")
public class DomainQualifierImpl extends AnnotationLiteral<Domain> implements Domain {
	private static final long serialVersionUID = 1L;
	private Class<?> value;

	public DomainQualifierImpl(Class<?> value) {
		super();
		this.value = value;
	}

	public Class<?> value() {
		return this.value;
	}

}
