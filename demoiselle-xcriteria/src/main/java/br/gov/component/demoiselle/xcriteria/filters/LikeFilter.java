package br.gov.component.demoiselle.xcriteria.filters;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.gov.component.demoiselle.xcriteria.annotation.Like;
import br.gov.component.demoiselle.xcriteria.base.XPredicate;

public class LikeFilter implements XPredicate<Predicate> {

	public boolean check(Field field, Class<?> fieldType, String fieldName, Object fieldValue) {
		return field.isAnnotationPresent(Like.class) && fieldType == String.class
				&& !StringUtils.isEmpty((String) fieldValue);
	}

	public <X> Predicate apply(CriteriaBuilder cb, Root<X> p, Class<?> fieldType, String name, Object value) {
		return cb.like(p.<String> get(name), "%" + value + "%");
	}

}
