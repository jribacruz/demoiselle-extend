package br.gov.frameworkdemoiselle.restriction2.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class FalseRestriction<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return hasField() ? cb.isFalse(p.<Boolean> get(getField())) : null;
	}

	@Override
	public String toString() {
		return "FalseRestriction [value=" + value + ", field=" + field + ", selection=" + selection + "]";
	}

}
