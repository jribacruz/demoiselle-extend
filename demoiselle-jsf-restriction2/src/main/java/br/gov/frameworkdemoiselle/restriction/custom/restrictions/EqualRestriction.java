package br.gov.frameworkdemoiselle.restriction.custom.restrictions;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Sets;

import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Strings;

public class EqualRestriction<T, X> extends RestrictionBean<T, X> {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<Predicate> restriction(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.selection == null || this.selection == Boolean.TRUE) {
			Iterator<String> iterator = this.fields.iterator();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				if (this.value != null && !Strings.isEmpty(fieldName)) {
					this.predicates.add(cb.equal(p.get(fieldName), getValue()));
				}
			}
		}
		return !this.predicates.isEmpty() ? Sets.newHashSet(cb.or(this.predicates.toArray(new Predicate[] {}))) : null;
	}

}
