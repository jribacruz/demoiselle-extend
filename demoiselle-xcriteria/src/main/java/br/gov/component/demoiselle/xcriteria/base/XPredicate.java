package br.gov.component.demoiselle.xcriteria.base;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public interface XPredicate<T> {
	public boolean check(Field field, Class<?> fieldType, String fieldName, Object fieldValue);

	public <X> T apply(CriteriaBuilder cb, Root<X> p, Class<?> fieldType, String fieldName, Object fieldValue);

}
