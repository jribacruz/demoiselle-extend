package br.gov.frameworkdemoiselle.restriction.custom.restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Strings;

public class StartWithRestriction<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return !Strings.isEmpty(getValue()) && hasField() ? cb.like(cb.lower(p.<String> get(getField())), getValue().toLowerCase()
				+ "%") : null;
	}

}
