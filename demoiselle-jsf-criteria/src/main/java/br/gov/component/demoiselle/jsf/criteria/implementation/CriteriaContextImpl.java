package br.gov.component.demoiselle.jsf.criteria.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.criteria.template.IRestriction;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private Class<? extends IRestriction> restrictionClass;

	private int pageSize;

	private String query;

	@SuppressWarnings("rawtypes")
	@Override
	public void setRestrictionClass(Class<? extends IRestriction> criteriaClass) {
		this.restrictionClass = criteriaClass;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setRestrictionClass(Class<? extends IRestriction> criteriaClass, int pageSize) {
		this.restrictionClass = criteriaClass;
		this.pageSize = pageSize;
	}

	@Override
	public <T> Class<? extends IRestriction> getRestrictionClass() {
		return this.restrictionClass;
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
	public <T, X> List<Predicate> getRestriction(CriteriaBuilder cb, Root<T> p) {
		IRestriction<T> restrictionClass = getRestrictionReference();
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (restrictionClass != null) {
			Predicate predicate = restrictionClass.restriction(cb, p);
			if (predicate != null) {
				predicates.add(predicate);
			}
		}
		return predicates;
	}

	@Override
	public <T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		IRestriction<T> restrictionClass = getRestrictionReference();
		if (restrictionClass != null) {
			restrictionClass.projection(cb, cq, p);
		}
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	@Override
	public void clear() {
		this.pageSize = 0;
		this.restrictionClass = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IRestriction<T> getRestrictionReference() {
		return restrictionClass != null ? Beans.getReference(restrictionClass) : null;
	}

}
