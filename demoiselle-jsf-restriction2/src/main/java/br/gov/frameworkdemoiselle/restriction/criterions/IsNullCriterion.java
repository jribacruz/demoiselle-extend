package br.gov.frameworkdemoiselle.restriction.criterions;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.CriterionBean;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.Sets;

public class IsNullCriterion<T> extends CriterionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> criterion(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.selection == null || this.selection == Boolean.TRUE) {
			Iterator<String> iterator = this.getFields().iterator();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				if (!Strings.isEmpty(fieldName)) {
					Predicate predicate = cb.isNotNull(p.get(fieldName));
					this.predicates.add(predicate);
				}
			}
		}
		return !this.predicates.isEmpty() ? Sets.newHashSet(cb.or(this.predicates.asPredicate())) : null;
	}
}
