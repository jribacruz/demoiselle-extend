package br.gov.frameworkdemoiselle.restriction.type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

public abstract class RestrictionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected X value;
	protected Set<String> fields = new HashSet<String>();
	protected Boolean selection = null;
	protected Set<Predicate> predicates = new HashSet<Predicate>();

	public abstract Set<Predicate> restriction(CriteriaBuilder cb, Root<T> p);

	public X getValue() {
		return value;
	}

	public void setValue(X value) {
		this.value = value;
	}

	public Set<String> getFields() {
		return fields;
	}

	public void setFields(Set<String> fields) {
		this.fields = fields;
	}

	public Boolean getSelection() {
		this.selection = Boolean.FALSE;
		return selection;
	}

	public void setSelection(Boolean selection) {
		this.selection = selection;
	}

	protected String lower(String str) {
		return StringUtils.lowerCase(str);
	}

	protected String upper(String str) {
		return StringUtils.upperCase(str);
	}

}
