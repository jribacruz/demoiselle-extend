package br.gov.frameworkdemoiselle.restriction.custom.restrictions;

import java.util.Collection;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;

public class NotContainsRestriction<T> extends RestrictionBean<T, Collection<T>> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> restriction(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.value != null && !this.value.isEmpty()) {
			this.predicates.add(cb.not(p.in(getValue())));
		}
		return !this.predicates.isEmpty() ? this.predicates : null;
	}
}
