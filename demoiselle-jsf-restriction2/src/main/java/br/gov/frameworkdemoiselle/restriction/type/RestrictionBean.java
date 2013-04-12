package br.gov.frameworkdemoiselle.restriction.type;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.util.Strings;

public abstract class RestrictionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected X value;
	protected String field;
	protected Boolean selection;

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

	public Boolean isSelection() {
		return selection;
	}

	public void setSelection(Boolean selection) {
		this.selection = selection;
	}

	protected boolean hasField() {
		return !Strings.isEmpty(field);
	}

	protected Collection<X> list(X... xs) {
		return Arrays.asList(xs);
	}

	protected String lower(String str) {
		return StringUtils.lowerCase(str);
	}

	protected String upper(String str) {
		return StringUtils.upperCase(str);
	}

}
