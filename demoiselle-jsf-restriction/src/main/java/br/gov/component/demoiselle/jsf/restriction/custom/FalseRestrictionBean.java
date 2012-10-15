package br.gov.component.demoiselle.jsf.restriction.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;

public class FalseRestrictionBean<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return isSelection() && hasField() ? cb.isFalse(p.<Boolean> get(getField())) : null;
	}

}
