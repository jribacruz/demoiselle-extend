package br.gov.frameworkdemoiselle.restriction.custom.restrictions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.Sets;

public class IsEmptyRestriction<T, X> extends RestrictionBean<T, Collection<X>> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> restriction(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.selection == null || this.selection == Boolean.TRUE) {
			Iterator<String> iterator = this.fields.iterator();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				if (!Strings.isEmpty(fieldName)) {
					this.predicates.add(cb.isEmpty(p.<Collection<X>> get(fieldName)));
				}
			}
		}
		return !this.predicates.isEmpty() ? Sets.newHashSet(cb.or(this.predicates.toArray(new Predicate[] {}))) : null;
	}

}