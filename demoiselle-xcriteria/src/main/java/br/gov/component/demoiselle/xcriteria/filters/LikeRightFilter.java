package br.gov.component.demoiselle.xcriteria.filters;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringUtils;

import br.gov.component.demoiselle.xcriteria.annotation.LikeRight;
import br.gov.component.demoiselle.xcriteria.base.XPredicate;

public class LikeRightFilter implements XPredicate<Predicate> {

	public boolean check(BeanMap beanMap, String name, Field field) {
		return field.getAnnotation(LikeRight.class) != null && !StringUtils.isEmpty((String) beanMap.get(name));
	}

	public <X> Predicate apply(CriteriaBuilder cb, Root<X> p, String fieldName, Object fieldValue) {
		return cb.like(p.<String> get(fieldName), fieldValue + "%");
	}

}
