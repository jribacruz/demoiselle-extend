package br.gov.component.demoiselle.jsf.restriction.template;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class RestrictionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;
	private X value;

	public abstract Predicate restriction(CriteriaBuilder cb, Root<T> p);

	public X getValue() {
		return value;
	}

	public void setValue(X value) {
		this.value = value;
	}
}