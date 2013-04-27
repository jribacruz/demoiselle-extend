package br.gov.frameworkdemoiselle.restriction.custom.criterions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.CriterionBean;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.Sets;

public class NotInCriterion<T, X> extends CriterionBean<T, Collection<X>> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> criterion(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.selection == null || this.selection == Boolean.TRUE) {
			Iterator<String> iterator = this.getFields().iterator();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				if (this.value != null && !this.value.isEmpty() && !Strings.isEmpty(fieldName)) {
					Predicate predicate = cb.not(p.get(fieldName).in(this.value));
					this.predicates.add(predicate);
				}
			}
		}
		return !this.predicates.isEmpty() ? Sets.newHashSet(cb.or(this.predicates.asPredicate())) : null;
	}

}
