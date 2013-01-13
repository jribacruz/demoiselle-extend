package br.gov.frameworkdemoiselle.restriction.internal.implementation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.Parameter;

@SessionScoped
public class ParameterImpl implements Parameter {
	private static final long serialVersionUID = 1L;

	private Map<String, String> filters;
	private SortOrder sortOrder;
	private String sortField;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		this.filters = new HashMap<String, String>();
	}

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

	@Override
	public void clear() {
		this.filters.clear();
		this.sortField = null;
		this.sortOrder = null;
	}

}
