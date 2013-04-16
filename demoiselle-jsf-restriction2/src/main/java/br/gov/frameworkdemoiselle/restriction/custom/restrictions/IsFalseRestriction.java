package br.gov.frameworkdemoiselle.restriction.custom.restrictions;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Sets;

import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Strings;

public class IsFalseRestriction<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> restriction(CriteriaBuilder cb, Root<T> p) {
		if (this.selection == null || this.selection == Boolean.TRUE) {
			Iterator<String> iterator = this.fields.iterator();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				if (!Strings.isEmpty(fieldName)) {
					this.predicates.add(cb.isFalse(p.<Boolean> get(fieldName)));
				}
			}
		}
		return Sets.newHashSet(cb.or(this.predicates.toArray(new Predicate[] {})));
	}

}
