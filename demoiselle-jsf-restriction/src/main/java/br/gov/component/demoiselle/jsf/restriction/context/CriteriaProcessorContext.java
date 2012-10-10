package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;

public interface CriteriaProcessorContext extends Serializable {
	@SuppressWarnings("rawtypes")
	void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass);

	@SuppressWarnings("rawtypes")
	Class<? extends AbstractCriteriaBean> getCriteriaControllerClass();

	<T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> p);

	<T> List<Order> getOrders(CriteriaBuilder cb, Root<T> p);

	<T> Predicate getHaving(CriteriaBuilder cb, Root<T> p);

	<T> Expression<?> groupBy(CriteriaBuilder cb, Root<T> p);

	<T> Selection<?> getProjection(CriteriaBuilder cb, Root<T> p);

	void clear();
}
