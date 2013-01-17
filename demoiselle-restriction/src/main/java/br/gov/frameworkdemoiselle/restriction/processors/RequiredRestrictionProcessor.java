package br.gov.frameworkdemoiselle.restriction.processors;

import java.lang.reflect.Field;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.base.Processor;
import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.restriction.utils.Restrictions;

public class RequiredRestrictionProcessor<T> implements Processor<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <C extends CriteriaBean<T>> Predicate apply(Field field, C criteriaBean, CriteriaBuilder cb, Root<T> p,
			Map<String, String> filters) {
		if (Restrictions.isRequiredRestriction(field)) {
			RestrictionBean bean = (RestrictionBean) Restrictions.getBean(field, criteriaBean);
			return Restrictions.callPredicate(bean, cb, p);
		}
		return null;
	}

}
