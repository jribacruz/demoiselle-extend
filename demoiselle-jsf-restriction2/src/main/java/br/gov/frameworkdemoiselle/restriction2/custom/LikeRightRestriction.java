package br.gov.frameworkdemoiselle.restriction2.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Strings;

public class LikeRightRestriction<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return !Strings.isEmpty(getValue()) && hasField() ? cb.like(cb.lower(p.<String> get(getField())), getValue().toLowerCase()
				+ "%") : null;
	}

}
