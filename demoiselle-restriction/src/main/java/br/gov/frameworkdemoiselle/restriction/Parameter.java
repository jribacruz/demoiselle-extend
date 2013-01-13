package br.gov.frameworkdemoiselle.restriction;

import java.io.Serializable;
import java.util.Map;

import org.primefaces.model.SortOrder;

public interface Parameter extends Serializable {

	public Map<String, String> filters();

	public void setFilters(Map<String, String> filters);

	public SortOrder sortOrder();

	public void setOrder(SortOrder sortOrder);

	public String sortField();

	public void setField(String sortField);

}
