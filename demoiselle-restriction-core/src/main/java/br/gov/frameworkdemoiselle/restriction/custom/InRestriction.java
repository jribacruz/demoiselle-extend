package br.gov.frameworkdemoiselle.restriction.custom;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;

public class InRestriction<T, X> extends RestrictionBean<T, Collection<X>> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return p.get(getField()).in(getValue());
	}

}
