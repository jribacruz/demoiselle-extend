package br.gov.component.demoiselle.jsf.restriction.custom;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;

public class NotMemberRestriction<T,X> extends RestrictionBean<T, X> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return cb.isNotMember(getValue(), p.<Collection<X>>get(getField()));
	}

}