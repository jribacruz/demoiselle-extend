package br.gov.component.demoiselle.jsf.restriction.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;

public class LikeLeftRestrictionBean<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return !StringUtils.isEmpty(getValue()) && isSelection() && hasField() ? cb.like(cb.lower(p.<String> get(getField())), "%" + getValue().toLowerCase()) : null;
	}

}
