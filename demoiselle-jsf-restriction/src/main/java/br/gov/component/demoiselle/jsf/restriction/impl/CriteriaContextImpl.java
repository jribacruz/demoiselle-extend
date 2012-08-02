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
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	private int pageSize;

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> criteriaBeanClass;

	@Override
	public void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass) {
		this.criteriaBeanClass = criteriaBeanClass;
	}

	@Override
	public Class<? extends AbstractCriteriaBean> getCriteriaControllerClass() {
		return this.criteriaBeanClass;
	}

	@Override
	public <T> List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			return bean != null ? bean.getPredicateList(cb, p) : new ArrayList<Predicate>();
		}
		return new ArrayList<Predicate>();
	}

	@Override
	public void clear() {
		this.criteriaBeanClass = null;
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public void setPageSize(int size) {
		this.pageSize = size;
	}

	@Override
	public <T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			if (bean != null) {
				bean.getProjection(cb, cq, p);
			}
		}

	}

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
}
