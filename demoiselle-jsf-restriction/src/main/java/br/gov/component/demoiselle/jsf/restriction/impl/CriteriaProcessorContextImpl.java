package br.gov.component.demoiselle.jsf.restriction.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaProcessorContextImpl implements CriteriaProcessorContext {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> criteriaBeanClass;

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
			return bean != null ? invokePredicateList(bean, cb, p) : new ArrayList<Predicate>();
		}
		return new ArrayList<Predicate>();
	}

	@SuppressWarnings("unchecked")
	private <T> List<Predicate> invokePredicateList(AbstractCriteriaBean<T> bean, CriteriaBuilder cb, Root<T> p) {
		try {
			return (List<Predicate>) MethodUtils.invokeMethod(bean, "getPredicateList", new Object[] { cb, p });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<Predicate>();
	}

	@SuppressWarnings("unchecked")
	public <T> List<Order> invokeOrderList(AbstractCriteriaBean<T> bean, CriteriaBuilder cb, Root<T> p) {
		try {
			return (List<Order>) MethodUtils.invokeMethod(bean, "getOrder", new Object[] { cb, p });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<Order>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Order> getOrderList(CriteriaBuilder cb, Root<T> p) {
		if (criteriaBeanClass != null) {
			AbstractCriteriaBean<T> bean = Beans.getReference(criteriaBeanClass);
			if (bean != null) {
				return invokeOrderList(bean, cb, p);
			}
		}
		return new ArrayList<Order>();
	}

	@Override
	public void clear() {
		this.criteriaBeanClass = null;
	}

}
