package br.gov.frameworkdemoiselle.restriction2.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;

public class LazyModel<T, C extends CriteriaBean<T>> extends LazyDataModel<T> {
	private static final long serialVersionUID = 1L;

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		return null;
	}
}
