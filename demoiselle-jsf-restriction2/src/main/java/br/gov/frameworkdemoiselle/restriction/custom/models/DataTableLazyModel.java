package br.gov.frameworkdemoiselle.restriction.custom.models;

import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.restriction.custom.criteria.DefaultDataTableCriteria;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	public DataTableLazyModel(DefaultDataTableCriteria<T> criteria) {
		super.criteria = criteria;
	}

}
