package br.gov.frameworkdemoiselle.restriction.custom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;

public class TrueRestriction<T> extends RestrictionBean<T, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return !StringUtils.isEmpty(getField()) ? cb.isTrue(p.<Boolean> get(getField())) : null;
	}

}
