package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.Map;

import org.primefaces.model.SortOrder;

public interface CriteriaContext extends Serializable {
	int getPageSize();

	void setPageSize(int size);

	void setQuery(String query);

	void setSortOrder(SortOrder sortOrder);

	SortOrder getSortOrder();

	void setFilters(Map<String, String> filters);

	public Map<String, String> getFilters();

	void setSortField(String sortField);

	String getSortField();

	public String getQuery();

	public void clear();

}
