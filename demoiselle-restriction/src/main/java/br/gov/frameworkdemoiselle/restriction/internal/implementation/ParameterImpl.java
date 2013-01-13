package br.gov.frameworkdemoiselle.restriction.internal.implementation;

import java.util.Map;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.restriction.Parameter;

@ViewScoped
public class ParameterImpl implements Parameter {
	private static final long serialVersionUID = 1L;

	private Map<String, String> filters;
	private SortOrder sortOrder;
	private String sortField;

	@Override
	public Map<String, String> filters() {
		return this.filters;
	}

	@Override
	public SortOrder sortOrder() {
		return this.sortOrder;
	}

	@Override
	public String sortField() {
		return this.sortField;
	}

	@Override
	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	@Override
	public void setOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public void setField(String sortField) {
		this.sortField = sortField;
	}

}
