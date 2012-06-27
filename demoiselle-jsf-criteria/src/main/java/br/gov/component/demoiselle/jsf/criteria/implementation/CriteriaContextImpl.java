package br.gov.component.demoiselle.jsf.criteria.implementation;

import javax.enterprise.context.SessionScoped;

import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.template.ICriteria;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	private Class<?> criteria;

	private int pageSize;

	public void setCriteria(Class<?> criteriaClass) {
		this.criteria = criteriaClass;
	}

	public void setCriteria(Class<?> criteriaClass, int pageSize) {
		this.criteria = criteriaClass;
		this.pageSize = pageSize;
	}

	@SuppressWarnings("unchecked")
	public <T> ICriteria<T> getCriteria() {
		return (ICriteria<T>) (criteria != null && ICriteria.class.isAssignableFrom(criteria) ? Beans.getReference(criteria)
				: null);
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int size) {
		this.pageSize = size;
	}

}
