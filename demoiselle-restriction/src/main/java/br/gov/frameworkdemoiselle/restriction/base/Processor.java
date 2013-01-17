package br.gov.frameworkdemoiselle.restriction.base;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

public interface Processor<T> {
	public <C extends CriteriaBean<T>> Predicate apply(Field field, C criteriaBean, CriteriaBuilder cb, Root<T> p);
}
