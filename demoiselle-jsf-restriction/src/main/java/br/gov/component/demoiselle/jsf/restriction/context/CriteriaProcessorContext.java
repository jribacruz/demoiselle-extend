package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.template.ProjectionBean;

public interface CriteriaProcessorContext extends Serializable {
	@SuppressWarnings("rawtypes")
	void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass);

	@SuppressWarnings("rawtypes")
	Class<? extends AbstractCriteriaBean> getCriteriaControllerClass();

	@SuppressWarnings("rawtypes")
	boolean setProjectionClass(Class<? extends ProjectionBean> projectionClass);

	@SuppressWarnings("rawtypes")
	Class<? extends ProjectionBean> getProjectionClass();

	<T> List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p);

	<T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);

	<T> List<Order> getOrderList(CriteriaBuilder cb, Root<T> p);

	void clear();
}
