package br.gov.component.demoiselle.jsf.restriction.template;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

public abstract class RestrictionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected X value;
	protected String field;
	protected boolean selection;

	public abstract Predicate restriction(CriteriaBuilder cb, Root<T> p);

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

	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}

	protected boolean hasField() {
		return !StringUtils.isEmpty(field);
	}

}
