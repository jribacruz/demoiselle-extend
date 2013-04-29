package br.gov.frameworkdemoiselle.restriction.custom.criterions;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.CriterionBean;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.Sets;

public class LikeCriterion<T> extends CriterionBean<T, String> {
	private static final long serialVersionUID = 1L;

	public LikeCriterion() {
		super();
	}

	public LikeCriterion(String value, String... fields) {
		super(value, fields);
	}

	public LikeCriterion(String value, Set<String> fields) {
		super(value, fields);
	}

	@Override
	public Set<Predicate> criterion(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (this.selection == null || this.selection == Boolean.TRUE) {
			Iterator<String> iterator = this.getFields().iterator();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				if (!Strings.isEmpty(this.value) && !Strings.isEmpty(fieldName)) {
					Predicate predicate = cb.like(cb.lower(p.<String> get(fieldName)), "%" + this.value.toLowerCase() + "%");
					this.predicates.add(predicate);
				}
			}
		}
		return !this.predicates.isEmpty() ? Sets.newHashSet(cb.or(this.predicates.asPredicate())) : null;
	}

}
