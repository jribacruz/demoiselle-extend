package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.custom.criteria.DefaultDataTableCriteria;
import br.gov.frameworkdemoiselle.util.Strings;

public class DefaultLazyModel<T> extends LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	protected DefaultDataTableCriteria<T> criteria;

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		if (!Strings.isEmpty(sortField)) {
			this.criteria.addOrder(sortOrder, sortField);
		}
		this.setRowCount(this.criteria.countAll());
		return this.criteria.findAll(first, pageSize);
	}

	public DefaultDataTableCriteria<T> getCriteria() {
		return criteria;
	}

	public void setCriteria(DefaultDataTableCriteria<T> criteria) {
		this.criteria = criteria;
	}

	public int size() {
		return this.getRowCount();
	}

}
