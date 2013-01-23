package br.gov.frameworkdemoiselle.restriction2.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class GreaterThanRestriction<T, X extends Number> extends RestrictionBean<T, X> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return getValue() != null && hasField() ? cb.gt(p.<Number> get(getField()), getValue()) : null;
	}

	@Override
	public String toString() {
		return "GreaterThanRestriction [value=" + value + ", field=" + field + ", selection=" + selection + "]";
	}

}
