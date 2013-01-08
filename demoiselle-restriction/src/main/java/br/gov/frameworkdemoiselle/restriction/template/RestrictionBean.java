package br.gov.frameworkdemoiselle.restriction.template;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class RestrictionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean selected;
	private X value;
	private String field;

	public abstract Predicate restriction(CriteriaBuilder cb, Root<T> p);

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public X getValue() {
		return value;
	}

	public void setValue(X value) {
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
