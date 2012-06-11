package br.gov.component.demoiselle.xcriteria.filters;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;

import br.gov.component.demoiselle.xcriteria.annotation.NotIn;
import br.gov.component.demoiselle.xcriteria.base.XPredicate;

public class NotInFilter implements XPredicate<Predicate> {

	public boolean check(BeanMap beanMap, String fieldName, Field field) {
		return field.getAnnotation(NotIn.class) != null && beanMap.get(fieldName) != null;
	}

	public <X> Predicate apply(CriteriaBuilder cb, Root<X> p, String name, Object value) {
		if (value.getClass().isAssignableFrom(Collection.class)) {
			return cb.not(p.get(name).in(value));
		}
		return null;
	}

}
