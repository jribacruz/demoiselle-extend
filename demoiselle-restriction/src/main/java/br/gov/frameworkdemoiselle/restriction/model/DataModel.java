package br.gov.frameworkdemoiselle.restriction.model;

import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

public abstract class DataModel<T, C extends CriteriaBean<T>> extends LazyDataModel<T> {
	private static final long serialVersionUID = 1L;

	private Map<String, String> filters;

	private SortOrder sortOrder;

	private String sortField;

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
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

}
