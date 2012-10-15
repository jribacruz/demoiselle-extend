package br.gov.component.demoiselle.jsf.restriction.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;

public class EqualRestrictionBean<T, X> extends RestrictionBean<T, X> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return getValue() != null && isSelection() && hasField() ? cb.equal(p.get(getField()), getValue()) : null;
	}

}
