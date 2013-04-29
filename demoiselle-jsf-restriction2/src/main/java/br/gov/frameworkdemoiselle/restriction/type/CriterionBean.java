package br.gov.frameworkdemoiselle.restriction.type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Sets;

import br.gov.frameworkdemoiselle.restriction.base.EPredicate;

public abstract class CriterionBean<T, X> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected X value;
	protected Set<String> fields = new HashSet<String>();
	protected Boolean selection = null;
	protected EPredicate predicates = new EPredicate();

	public CriterionBean() {
		super();
	}

	public CriterionBean(X value, String... fields) {
		super();
		this.value = value;
		this.fields = Sets.newHashSet(fields);
	}

	public CriterionBean(X value, Set<String> fields) {
		super();
		this.value = value;
		this.fields = fields;
	}

	public abstract Set<Predicate> criterion(CriteriaBuilder cb, Root<T> p);

	public X getValue() {
		return value;
	}

	public void setValue(X value) {
		this.value = value;
	}

	public Set<String> getFields() {
		return this.fields;
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

}
