package br.gov.component.demoiselle.jsf.restriction.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restriction.template.ProjectionBean;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaProcessorContextImpl implements CriteriaProcessorContext {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> criteriaBeanClass;

	@SuppressWarnings({ "rawtypes" })
	private Class<? extends ProjectionBean> projectionClass;

	@SuppressWarnings("rawtypes")
	@Override
	public void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass) {
		logger.info("Criteria {}", criteriaBeanClass.getName());
		this.criteriaBeanClass = criteriaBeanClass;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends AbstractCriteriaBean> getCriteriaControllerClass() {
		return this.criteriaBeanClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			return bean != null ? bean.getPredicateList(cb, p) : new ArrayList<Predicate>();
		}
		return new ArrayList<Predicate>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		if (projectionClass != null) {
			ProjectionBean<T> bean = Beans.getReference(projectionClass);
			if (bean != null) {
				bean.projection(cb, cq, p);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Order> getOrderList(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			if (bean != null) {
				return bean.getOrder(cb, p);
			}
		}
		return new ArrayList<Order>();
	}

	@Override
	public void clear() {
		this.criteriaBeanClass = null;
		this.projectionClass = null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean setProjectionClass(Class<? extends ProjectionBean> projectionClass) {
		logger.info("Projection {}", projectionClass.getName());
		this.projectionClass = projectionClass;
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends ProjectionBean> getProjectionClass() {
		return this.projectionClass;
	}

}
