package br.gov.frameworkdemoiselle.restriction.context;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.base.EOrder;
import br.gov.frameworkdemoiselle.util.Reflections;

public class ModelContext<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int maxResults;

	private EOrder orders;

	private String[] queryAttributes;

	private String query;

	private Class<T> beanClass;

	private Field fieldBeanClass;

	private Class<? extends CriteriaBean<T>> criteriaClass;

	public ModelContext() {
		super();
		this.orders = new EOrder();
	}

	public void setAsc(String[] asc) {
		this.orders.add(SortOrder.ASCENDING, asc);
	}

	public void setDesc(String[] desc) {
		this.orders.add(SortOrder.DESCENDING, desc);
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public String[] getQueryAttributes() {
		if (this.queryAttributes == null) {
			return new String[] {};
		}
		return queryAttributes;
	}

	public void setQueryAttributes(String[] queryAttributes) {
		this.queryAttributes = queryAttributes;
	}

	public void setSortOrder(SortOrder sortOrder, String attribute) {
		this.orders.add(sortOrder, attribute);
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Class<? extends CriteriaBean<T>> getCriteriaClass() {
		return criteriaClass;
	}

	public void setCriteriaClass(Class<? extends CriteriaBean<T>> criteriaClass) {
		this.criteriaClass = criteriaClass;
	}

	public void setFieldBeanClass(Field fieldBeanClass) {
		this.fieldBeanClass = fieldBeanClass;
	}

	public Class<T> getBeanClass() {
		if (this.beanClass == null) {
			this.beanClass = Reflections.getGenericTypeArgument(this.fieldBeanClass, 0);
		}
		return this.beanClass;
	}

	public EOrder getOrders() {
		return this.orders;
	}

}
