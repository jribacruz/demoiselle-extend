package br.gov.frameworkdemoiselle.restriction2.custom;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class ContainsRestriction<T> extends RestrictionBean<T, Collection<T>> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return getValue() != null ? p.in(getValue()) : null;
	}

	@Override
	public String toString() {
		return "ContainsRestriction [value=" + value + ", field=" + field + ", selection=" + selection + "]";
	}

}
