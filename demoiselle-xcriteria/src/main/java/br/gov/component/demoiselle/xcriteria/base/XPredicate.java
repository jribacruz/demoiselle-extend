package br.gov.component.demoiselle.xcriteria.base;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;

public interface XPredicate<T> {
	public boolean check(BeanMap beanMap, String name, Field field);

	public <X> T apply(CriteriaBuilder cb, Root<X> p, String fieldName, Object fieldValue);

}
