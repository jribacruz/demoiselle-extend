package br.gov.component.demoiselle.xcriteria.base;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public interface XPredicate<T> {
	public boolean check(List<Annotation> annotations, Class<?> fieldType, String fieldName, Object fieldValue);

	public <X> T apply(CriteriaBuilder cb, Root<X> p, String fieldName, Object fieldValue);

}
