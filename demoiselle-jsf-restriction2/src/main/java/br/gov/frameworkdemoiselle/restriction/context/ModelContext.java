package br.gov.frameworkdemoiselle.restriction.context;

import java.io.Serializable;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.CriteriaBean;

public class ModelContext<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[] asc;

	private String[] desc;

	private int maxResults;

	private String[] queryAttributes;

	private SortOrder sortOrder;

	private String sortField;

	private String query;

	private Class<? extends CriteriaBean<T>> criteriaClass;

	public String[] getAsc() {
		return asc;
	}

	public void setAsc(String[] asc) {
		this.asc = asc;
	}

	public String[] getDesc() {
		return desc;
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

}
