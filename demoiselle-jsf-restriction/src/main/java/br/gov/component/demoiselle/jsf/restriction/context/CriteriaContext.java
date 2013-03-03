package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.Map;

import org.primefaces.model.SortOrder;

public interface CriteriaContext extends Serializable {
	void setSortOrder(SortOrder sortOrder);

	SortOrder getSortOrder();

	void setFilters(Map<String, String> filters);

	public Map<String, String> getFilters();

	void setSortField(String sortField);

	String getSortField();

	public void clear();

}
