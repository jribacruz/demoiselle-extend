package br.gov.frameworkdemoiselle.restriction.custom.restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;

public class LessThanOrEqualToRestriction<T, X extends Number> extends RestrictionBean<T, X> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return getValue() != null && hasField() ? cb.le(p.<Number> get(getField()), getValue()) : null;
	}

}
