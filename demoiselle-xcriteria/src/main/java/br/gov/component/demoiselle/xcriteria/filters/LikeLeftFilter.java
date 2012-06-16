package br.gov.component.demoiselle.xcriteria.filters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.gov.component.demoiselle.xcriteria.annotation.LikeLeft;
import br.gov.component.demoiselle.xcriteria.base.XPredicate;

public class LikeLeftFilter implements XPredicate<Predicate> {
	public boolean check(Field field, Class<?> fieldType, String fieldName, Object fieldValue) {
		return field.isAnnotationPresent(LikeLeft.class) && fieldType == String.class
				&& !StringUtils.isEmpty((String) fieldValue);
	}

	public <X> Predicate apply(CriteriaBuilder cb, Root<X> p, Class<?> fieldType, String fieldName, Object fieldValue) {
		return cb.like(p.<String> get(fieldName), "%" + fieldValue);
	}

}
