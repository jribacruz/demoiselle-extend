package br.gov.component.demoiselle.jsf.criteria;

import java.io.Serializable;

import javax.inject.Inject;

import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;

public abstract class AbstractCriteria<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private SortOrder sortOrder;
	private String sortField;
	private String query;

	@Inject
	protected CriteriaContext context;

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
		context.setQuery(query);
		this.query = query;
	}
}
