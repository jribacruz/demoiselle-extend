package br.gov.frameworkdemoiselle.restriction.custom.models;

import br.gov.frameworkdemoiselle.restriction.core.Criteria;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	public DataTableLazyModel(Criteria<T> criteria) {
		super.criteria = criteria;
	}

}
