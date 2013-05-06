package br.gov.frameworkdemoiselle.restriction.criterions;

import java.util.Collection;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.CriterionBean;

public class NotContainsCriterion<T> extends CriterionBean<T, Collection<T>> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> criterion(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.value != null && !this.value.isEmpty()) {
			Predicate predicate = cb.not(p.in(getValue()));
			this.predicates.add(predicate);
		}
		return !this.predicates.isEmpty() ? this.predicates : null;
	}
}
