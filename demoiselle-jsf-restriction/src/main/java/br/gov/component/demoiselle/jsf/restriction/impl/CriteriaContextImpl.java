package br.gov.component.demoiselle.jsf.restriction.impl;

import java.util.Map;

import javax.enterprise.context.SessionScoped;

import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	private String sortField;

	private SortOrder sortOrder;

	private Map<String, String> filters;

	@Override
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public SortOrder getSortOrder() {
		return this.sortOrder;
	}

	@Override
	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	@Override
	public Map<String, String> getFilters() {
		return this.filters;
	}

	@Override
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	@Override
	public String getSortField() {
		return this.sortField;
	}

	@Override
	public void clear() {
		this.sortField = null;
		this.sortOrder = null;
		this.filters.clear();
	}

}
