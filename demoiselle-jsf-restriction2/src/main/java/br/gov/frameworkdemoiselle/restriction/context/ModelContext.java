package br.gov.frameworkdemoiselle.restriction.context;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.CriteriaBean;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public class ModelContext<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[] asc;

	private String[] desc;

	private int maxResults;

	private String[] queryAttributes;

	private SortOrder sortOrder;

	private String sortField;

	private String query;

	private Class<T> beanClass;

	private Field fieldBeanClass;

	private Class<? extends CriteriaBean<T>> criteriaClass;

	public List<Order> getAsc(CriteriaBuilder cb, Root<T> p) {
		List<Order> list = new ArrayList<Order>();

		if (this.asc != null) {
			for (int i = 0; i < this.asc.length; i++) {
				if (!Strings.isEmpty(asc[i])) {
					list.add(cb.asc(p.get(asc[i])));
				}
			}
		}

		if (!Strings.isEmpty(this.sortField) && this.sortOrder == SortOrder.ASCENDING) {
			list.add(cb.asc(p.get(this.sortField)));
		}

		return list;
	}

	public void setAsc(String[] asc) {
		this.asc = asc;
	}

	public List<Order> getDesc(CriteriaBuilder cb, Root<T> p) {
		List<Order> list = new ArrayList<Order>();

		if (this.desc != null) {
			for (int i = 0; i < this.desc.length; i++) {
				if (!Strings.isEmpty(desc[i])) {
					list.add(cb.desc(p.get(desc[i])));
				}
			}
		}

		if (!Strings.isEmpty(this.sortField) && this.sortOrder == SortOrder.DESCENDING) {
			list.add(cb.desc(p.get(this.sortField)));
		}

		return list;

	}

	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public String[] getQueryAttributes() {
		return queryAttributes;
	}

	public void setQueryAttributes(String[] queryAttributes) {
		this.queryAttributes = queryAttributes;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
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

}
