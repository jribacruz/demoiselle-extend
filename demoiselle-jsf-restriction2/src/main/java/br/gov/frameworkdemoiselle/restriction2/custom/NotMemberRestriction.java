package br.gov.frameworkdemoiselle.restriction2.custom;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class NotMemberRestriction<T, X> extends RestrictionBean<T, X> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return cb.isNotMember(getValue(), p.<Collection<X>> get(getField()));
	}

	@Override
	public String toString() {
		return "NotMemberRestriction [value=" + value + ", field=" + field + ", selection=" + selection + "]";
	}

}
