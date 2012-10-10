package br.gov.component.demoiselle.jsf.restriction.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restrictions.util.Utils;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaProcessorContextImpl implements CriteriaProcessorContext {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> criteriaBeanClass;

	@SuppressWarnings("rawtypes")
	@Override
	public void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass) {
		this.criteriaBeanClass = criteriaBeanClass;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends AbstractCriteriaBean> getCriteriaControllerClass() {
		return this.criteriaBeanClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);

			if (bean != null) {
				return Utils.<Predicate> invokeMethodReturnCollection(bean, "getPredicates", cb, p);
			}
		}
		return new ArrayList<Predicate>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Order> getOrders(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);

			if (bean != null) {
				return Utils.<Order> invokeMethodReturnCollection(bean, "getOrders", cb, p);
			}
		}
		return new ArrayList<Order>();
	}

	@Override
	public void clear() {
		this.criteriaBeanClass = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Predicate getHaving(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			if (bean != null) {
				return Utils.invokeMethod(bean, "having", cb, p);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Expression<?> groupBy(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			if (bean != null) {
				return Utils.invokeMethod(bean, "groupBy", cb, p);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Selection<?> getProjection(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			if (bean != null) {
				return Utils.invokeMethod(bean, "projection", cb, p);
			}
		}
		return null;
	}

}
