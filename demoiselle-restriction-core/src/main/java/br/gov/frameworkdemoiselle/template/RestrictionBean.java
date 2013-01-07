package br.gov.frameworkdemoiselle.template;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RestrictionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean selection;
	private X value;
	private String field;

	protected Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
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
